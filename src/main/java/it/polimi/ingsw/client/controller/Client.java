package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.ClientObserver;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.controller.Observable;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.util.ArrayList;


public abstract class Client implements Observable<ClientObserver>{


    private int port;

    private String ip;

    private boolean isLogged;

    private ClientGame clientGame;

    private ArrayList<ClientObserver> observers;

    private PrivateObjectiveCard privateObjectiveCard;

    private String username;

    private boolean actionMoveDone;
    private boolean actionToolCardDone;

    public Client(String ip, int port){
        observers=new ArrayList<ClientObserver>();
        this.port = port;
        this.ip = ip;
        clientGame = new ClientGame();

    }

    public abstract void connectToServer() throws Exception;

    /**
     * tries to log into the game on the server
     * @param username player username
     * @param password player password
     * @throws AlreadyLoggedException
     * @throws GameFullException
     * @throws WrongPasswordException
     * @throws NoUserException
     * @throws GameStartedException
     * @throws PlayerRiconnectionException
     */

    public abstract void login(String username, String password) throws AlreadyLoggedException, GameFullException, WrongPasswordException, NoUserException, GameStartedException, PlayerRiconnectionException;

    /**
     * tries to save new user into server db
     * @param username player username
     * @param password player password
     * @throws AlreadyUsedUsernameException
     */

    public abstract void signIn(String username, String password) throws AlreadyUsedUsernameException;

    /**
     * sends selected pattern to the server
     * @param patternSelected player selected pattern
     */

    public abstract void patternSelected(Pattern patternSelected);

    /**
     * sends the end turn notification to server
     */

    public abstract void endTurn();

    /**
     * sends end tool 11 to server
     * @param dieNumber chosen die number
     */

    public abstract void endTool11(int dieNumber);

    /**
     * sends player move to server
     * @param diePosition reserve die position
     * @param x cell x
     * @param y cell y
     */

    public abstract void moveDie(int diePosition, int x, int y);

    /**
     * sends use toolcard move to server
     * @param toolCardNumber number of toolCard
     * @param params parameter needed from toolCard
     */

    public abstract void useToolCard(int toolCardNumber, ArrayList<Integer> params);


    public void notifySuccessfullLogin(String username){
        for(ClientObserver obs: observers){
            obs.onSuccessfullLogin(username);
        }
    }

    public void notifySuccessfullSignIn(String username){
        for(ClientObserver obs: observers){
            obs.onSuccessfullSignIn(username);
        }
    }

    // Notifications to the UserInterface

    public void notifySetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa){

        for(ClientObserver obs: observers) {
            obs.onSetUpReceived(pr, pu, pa);
        }
    }

    public void notifyExceptionLaunched(String exceptionName, String errorMessage){
        for(ClientObserver obs: observers){
            obs.onExceptionLaunched(exceptionName, errorMessage);
        }
    }

    public void notifyPatternReceived(ArrayList<ClientPlayer> player, String username){
        for (ClientObserver obs: observers){
            obs.onPatternReceived(player, username);
        }
    }

    public void notifyReserveReceived(ArrayList<Die> reserve){
        for (ClientObserver obs: observers){
            obs.onReserveReceived(reserve);
        }
    }

    public void notifyClientGameReceived(ClientGame clientGame){

        for (ClientObserver obs: observers){
            obs.onClientGameReceived(clientGame);
        }

    }

    public void notifyYourTurn(ClientGame clientGame){

        for (ClientObserver obs: observers){
            obs.isYourTurn(clientGame);
        }

    }

    public void notifyNotYourTurn(){

        for (ClientObserver obs: observers){
            obs.onNotYourTurn();
        }

    }

    public void notifyEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores){

        for ( ClientObserver obs : observers ){

            obs.onEndGame(leaderboard, scores);
        }

    }

    public void notifyForcedPatternChoice(){

        for ( ClientObserver obs : observers ){

            obs.onForcedPatternChoice();
        }

    }

    public void notifyDieTool11(Die die){
        for ( ClientObserver obs : observers ){

            obs.onDieTool11Received(die);
        }
    }




    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public ClientGame getClientGame() {
        return clientGame;
    }

    public void setClientGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public String getUsername(){
        return username;
    }

    public boolean isActionMoveDone() {
        return actionMoveDone;
    }

    public void setActionMoveDone(boolean actionMoveDone) {
        this.actionMoveDone = actionMoveDone;
    }

    public boolean isActionToolCardDone() {
        return actionToolCardDone;
    }

    public void setActionToolCardDone(boolean actionToolCardDone) {
        this.actionToolCardDone = actionToolCardDone;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public void addObserver(ClientObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ClientObserver observer) {
        observers.remove(observer);
    }

    public void removeAllObservers(){
        for(ClientObserver observer:observers){
            observers.remove(observer);
        }
    }


}
