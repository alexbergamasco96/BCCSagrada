package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.Database;
import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.client.controller.SocketClient.PatternNumberParser;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.controller.RMI.RMIServer;
import it.polimi.ingsw.server.controller.socket.SocketServer;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utility.GameConfig;
import it.polimi.ingsw.utility.GsonReader;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameController implements TimerObserver{

    private final static int SOCKETPORT = 1996;
    private final static int RMIPORT = 1099;

    private SocketServer socketServer;
    private RMIServer rmiServer;
    private Game game;
    private GameTimer timer;
    private Boolean started;
    private ArrayList<Player> activePlayers;
    private GameConfig gameConfig;
    private int currentRound;
    private ClientGame clientGame;
    private ArrayList<ClientPlayer> activeClientPlayers;
    private GsonReader gsonReader;



    public GameController(){
        gameConfig = new GameConfig();
        gameConfig.setGameConfig();

        started = false;
        socketServer = new SocketServer(gameConfig.getSOCKET_PORT(),this);
        socketServer.start();
        try{
            rmiServer = new RMIServer(gameConfig.getRMI_PORT(), this);
        }catch(RemoteException e){
            e.printStackTrace();
        }
        activePlayers = new ArrayList<Player>();
        game = new Game();
        currentRound = 1;

    }


    /**
     * Sets active players
     * @param activePlayers array of player connected
     */

    public void setActiveClientPlayers(ArrayList<Player> activePlayers){

        ArrayList<ClientPlayer> a = new ArrayList<ClientPlayer>();
        for(Player p: activePlayers){
            a.add(new ClientPlayer(p.getNickname(),p.getPattern(),p.getToken()));
        }
        activeClientPlayers=a;

    }

    /**
     * tries to log a client in the game looking into the db
     * @param username player name
     * @param password player password
     * @param clientHandler clientHandler linked to client
     * @return true for successfull login
     * @throws AlreadyLoggedException
     * @throws WrongPasswordException
     * @throws NoUserException
     * @throws GameFullException
     * @throws GameStartedException
     */

    public synchronized boolean Login(String username, String password, ClientHandler clientHandler) throws AlreadyLoggedException, WrongPasswordException, NoUserException, GameFullException, GameStartedException {

        Database db = Database.getDb();

        if (!started) {

            System.out.println("Tento login");
            if (db.login(username, password)) {
                System.out.println("Logged");
                game.addPlayer(new Player(username, clientHandler));
                if (game.playersSize() == 2) {
                    timer = new GameTimer(gameConfig.getTIMEOUT(),TimerHeader.GAME);
                    timer.addObserver(this);
                    System.out.println("TIMER AVVIATO");
                }
                return true;
            }
        }
        else{
            if(db.isLogged(username,password) ){
                if(!game.getPlayerFromName(username).getConnected()) {

                    game.getPlayerFromName(username).setClientHandler(clientHandler);
                    reconnect(username);
                    game.getPlayerFromName(username).getClientHandler().sendForcedGameStarted(game.getPlayerFromName(username).getNickname(),game.getPlayerFromName(username).getPrivateObjectiveCard(),game.getPublicObjectiveCards());
                    try {
                        wait(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendClientGame();
                    return false;
                }
                else{
                    throw new AlreadyLoggedException();
                }
            }
        }

        throw new GameStartedException(username);

    }

    /**
     * tries to save a new client into the db
     * @param username player username
     * @param password player password
     * @throws AlreadyUsedUsernameException
     */

    public synchronized void signIn(String username, String password) throws AlreadyUsedUsernameException{
        Database db = Database.getDb();

        db.signIn(username,password);
    }

    /**
     * sets up the game extracting private and public cards
     */

    public void setUpGame(){

        game.extractPrivateObjectiveCard();
        game.extractPublicObjectiveCard();

    }

    /**
     * sets chosen pattern to his player
     * @param username player username
     * @param p chosen pattern
     */

    public void setPattern(String username, Pattern p){


        game.getPlayerFromName(username).setPattern(p);
        game.getPlayerFromName(username).setToken(p.getDifficulty());
        activePlayers.add(game.getPlayerFromName(username));

        //when all player have chosen
        if(activePlayers.size() == game.getPlayers().size()){
            timer.setNotActive(); //set not active timer pattern-choice


            game.setUp(activePlayers);
            setActiveClientPlayers(activePlayers);
            clientGame = new ClientGame(game.getPublicObjectiveCards(),activeClientPlayers,game.getRoundTrack(),game.extractReserve(activePlayers.size()),game.getCurrentRound().getActivePlayer().getNickname(), game.getToolCards());

            sendClientGame();


            startTurnTimer();

        }

    }

    /**
     * executes move die action from clients
     * @param username player username
     * @param diePosition die position in the reserve
     * @param x cell x
     * @param y cell y
     * @throws IllegalActionException
     */

    public void moveDie(String username, int diePosition, int x, int y) throws IllegalActionException {
        if(x==-1&&y==-1){
            throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
        }

        if(game.getCurrentRound().getActivePlayer().getForcedDie() != null){

            if(game.getCurrentRound().getActivePlayer().getForcedDie() != game.getCurrentRound().getReserve().get(diePosition)){
                throw new IllegalActionException("Devi piazzare il dado obbligatoriamente! Lo puoi fare");
            }

        }

        game.getPlayerFromName(username).placeDie(game.getCurrentRound().getReserve().get(diePosition),x,y);
        game.getCurrentRound().getReserve().remove(diePosition);

        game.getCurrentRound().getActivePlayer().setForcedDie(null);

        clientGame.getPlayerFromName(username).setPattern(game.getPlayerFromName(username).getPattern());
        clientGame.setReserve(game.getCurrentRound().getReserve());


        game.getPlayerFromName(username).getClientHandler().sendResponse(new ResponseNetObject(MessageHeaderEnum.RESPONSE.toString(),MessageHeaderEnum.MOVE.toString()));

        sendClientGame();

    }

    /**
     * jumps to next turn notifying clients
     * @param username player username
     */

    public void nextTurn(String username){
        timer.setNotActive();
        int roundNumber = game.getCurrentRound().getN();
        //if there is a next turn or a next round
        if(game.nextTurn()) {
            clientGame.setTurnNumber(game.getCurrentRound().getTurn());
            // if round changed
            if (roundNumber != game.getCurrentRound().getN()) {
                //extract new reserve
                clientGame.setReserve(game.extractReserve(activePlayers.size()));

                clientGame.setRoundTrack(game.getRoundTrack());
                clientGame.setRoundNumber(game.getCurrentRound().getN());
                System.out.println("CAMBIO ROUND");
            }
            clientGame.setActivePlayerUsername(game.getCurrentRound().getActivePlayer().getNickname());
            //if current player is disconnected -> jump turn
            if(!clientGame.getPlayerFromName(clientGame.getActivePlayerUsername()).isConnected()){
                nextTurn("");
            }else {

                sendClientGame();

                startTurnTimer();
            }
        }
        //else the game is finished
        else{
            System.out.println("------ GIOCO FINITO ! ------");
            endGame();
        }
    }

    /**
     * executes use toolCard action from clients
     * @param username player username
     * @param toolcardNumber tool card number
     * @param params parameters needed from the toolcard
     * @throws IllegalActionException
     * @throws WrongResources
     */

    public void useToolCard(String username, int toolcardNumber, ArrayList<Integer> params) throws IllegalActionException, WrongResources{

        game.getToolCards().get(toolcardNumber-1).useToolCard(game, params);
        if (game.getToolCards().get(toolcardNumber-1).getNumber()==11){
            game.getPlayerFromName(username).getClientHandler().sendDieTool11(game.getCurrentRound().getReserve().get(game.getCurrentRound().getReserve().size()-1));
            clientGame.setReserve(game.getCurrentRound().getReserve());
            clientGame.getPlayerFromName(username).setToken(game.getCurrentRound().getActivePlayer().getToken());
            clientGame.setToolCards(game.getToolCards());
            return;
        }
        //clientGame.setToolCards(game.getToolCards());
        clientGame.getPlayerFromName(username).setPattern(game.getPlayerFromName(username).getPattern());
        clientGame.setReserve(game.getCurrentRound().getReserve());
        clientGame.getPlayerFromName(username).setToken(game.getCurrentRound().getActivePlayer().getToken());
        clientGame.setToolCards(game.getToolCards());

        game.getPlayerFromName(username).getClientHandler().sendResponse(new ResponseNetObject(MessageHeaderEnum.RESPONSE.toString(),MessageHeaderEnum.TOOLCARD.toString()));

        sendClientGame();
    }

    /**
     * handles toolCard 11 double netObject
     * @param dieNumber chosen die number
     */

    public void endTool11(int dieNumber){
        game.getCurrentRound().getReserve().get(game.getCurrentRound().getReserve().size()-1).setNumber(dieNumber);

        boolean old = true;
        if(!game.getCurrentRound().getActivePlayer().getPattern().isFirsPlace()){
            old = false;
        }
        if(game.getCurrentRound().getActivePlayer().getPattern().checkForcedPlacement(game.getCurrentRound().getReserve().get(game.getCurrentRound().getReserve().size()-1))){


            game.getCurrentRound().getActivePlayer().setForcedDie(game.getCurrentRound().getReserve().get(game.getCurrentRound().getReserve().size()-1));

            if(!old){
                game.getCurrentRound().getActivePlayer().getPattern().setFirsPlace(false);
            }

        }

        clientGame.setReserve(game.getCurrentRound().getReserve());
        sendClientGame();
    }

    /**
     * ends game and send scores to clients
     */

    public void endGame(){

        //Send leaderboard to all clients (Create leaderboard.class)
        game.calculatePoint();
        Database db = Database.getDb();
        //clientGame.setActivePlayerUsername("XXX");
        for(Player player : activePlayers){
            System.out.println("invio client game FINALE a "+player.getNickname());
            //player.getClientHandler().sendClientGame(clientGame);


            if (db.getBestScore(player.getNickname())<game.getLeaderboard().getPlayerPlacement(player.getNickname()).getPoint()){
                db.setBestScore(player.getNickname(),game.getLeaderboard().getPlayerPlacement(player.getNickname()).getPoint());
            }

            player.getClientHandler().sendEndGame(game.getLeaderboard(),db.getBestTen());
        }

    }

    /**
     * sets game started
     */

    public void setStarted(){
        started=true;
    }


    /**
     * handles timers finish notification
     * @param timerHeader timer type header
     */

    @Override
    public void onTimerFinished(String timerHeader) {

        if(timerHeader.equals(TimerHeader.GAME.toString())) {
            System.out.println("TIMER FINITO -> NOTIFICA ARRIVATA AL GAMECONTROLLER");
            setStarted();
            setUpGame();

            for (Player p : game.getPlayers()) {
                p.setPatternNumbersExtracted(game.extractPlayerPatterns());
                System.out.println(p.getPatternNumbersExtracted());
                p.getClientHandler().sendSetUp(p.getPrivateObjectiveCard(), game.getPublicObjectiveCards(), p.getPatternNumbersExtracted());
            }


            System.out.println("TIMER PATTERN_CHOICE STARTED");
            timer = new GameTimer(gameConfig.getPATTERNCHOICE_TIMEOUT(),TimerHeader.PATTERNCHOICE);
            timer.addObserver(this);

        }
        else if(timerHeader.equals(TimerHeader.PATTERNCHOICE.toString())){
            System.out.println("PATTERNCHOICE-TIMER FINITO --> SETTO PATTERN E INVIO CLIENT GAME");

            for(Player p : game.getPlayers()){
                if(!activePlayers.contains(p)){
                    PatternNumberParser patternNumberParser = new PatternNumberParser();
                    //Pattern chosenPattern = patternNumberParser.parseSingle(p.getPatternNumbersExtracted().get(0),true);
                    Pattern chosenPattern = patternNumberParser.parseSingle(p.getPatternNumbersExtracted().get(0));
                    p.setPattern(chosenPattern);
                    p.setToken(chosenPattern.getDifficulty());
                    activePlayers.add(p);
                    p.getClientHandler().sendForcedGameStarted(p.getNickname(),p.getPrivateObjectiveCard(),game.getPublicObjectiveCards());
                }
            }

            game.setUp(activePlayers);
            setActiveClientPlayers(activePlayers);
            clientGame = new ClientGame(game.getPublicObjectiveCards(),activeClientPlayers,game.getRoundTrack(),game.extractReserve(activePlayers.size()),game.getCurrentRound().getActivePlayer().getNickname(), game.getToolCards());

            sendClientGame();


            startTurnTimer();
        }
        else if(timerHeader.equals(TimerHeader.TURN.toString())){
            System.out.println("TURN_TIMER FINITO --> CAMBIO TURNO");
            nextTurn("");
        }
    }

    /**
     * sets disconnected the player
     * @param username player username
     */

    public void disconnect(String username){
        game.getPlayerFromName(username).setConnected(false);
        clientGame.getPlayerFromName(username).setConnected(false);
        System.out.println(username + "----> DISCONNESSO");
    }

    /**
     * sets player as reconnected
     * @param username player username
     */

    public void reconnect(String username){
        game.getPlayerFromName(username).setConnected(true);
        game.getPlayerFromName(username).getClientHandler().setUsername(username);
        clientGame.getPlayerFromName(username).setConnected(true);
    }


    public ClientGame getClientGame() {
        return clientGame;
    }

    /**
     * start a new turnTimer
     */

    private void startTurnTimer(){
        timer = new GameTimer(gameConfig.getTURN_TIMEOUT(),TimerHeader.TURN);
        timer.addObserver(this);
    }

    /**
     * sends the clientGame to each client
     */

    private void sendClientGame(){
        for(Player player:activePlayers){
            if(player.getConnected()) {
                player.getClientHandler().sendClientGame(clientGame);
                System.out.println("Clientgame inviato a "+player.getNickname());
            }
        }
    }


    public static void main(String[] args){
        Database db = Database.getDb();
        db.reset();
        GameController gameController = new GameController();
    }


}
