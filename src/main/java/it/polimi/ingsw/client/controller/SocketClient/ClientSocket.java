package it.polimi.ingsw.client.controller.SocketClient;

import it.polimi.ingsw.client.controller.*;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.netObject.*;
import it.polimi.ingsw.netObject.response.DieTool11ResponseObject;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.io.IOException;
import java.net.Socket;

/*
 * Created by cisla73 on 09/05/18
 */


public class ClientSocket extends Client implements ResponseParserObserver {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ResponseObjectParser responseParser;
    private String username;
    private Receiver receiver;


    private final static int CONN_TIMEOUT = 10000;

    public ClientSocket(String ip, int port) throws Exception{

        super(ip,port);


            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),CONN_TIMEOUT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            responseParser = new ResponseObjectParser();
            responseParser.addObserver(this);
            receiver=new Receiver(in,out,responseParser);
            setLogged(false);

    }

    @Override
    public void connectToServer() throws Exception{

    }


    @Override
    public void login(String username, String password) throws AlreadyLoggedException, GameFullException, WrongPasswordException, NoUserException {

        LoginNetObject loginNetObject = new LoginNetObject(MessageHeaderEnum.LOGIN.toString(),username,password);

        //send sendAction data
        sendObject(loginNetObject);

        //responseParser.parse(serverResponse);

    }

    @Override
    public void signIn(String username, String password) throws AlreadyUsedUsernameException {
        SignInNetObject signInNetObject = new SignInNetObject(username, password);

        sendObject(signInNetObject);
    }

    @Override
    public void patternSelected(Pattern patternSelected) {

        PatternSelectedNetObject patternSelectedNetObject = new PatternSelectedNetObject(MessageHeaderEnum.PATTERN.toString(),patternSelected,username);

        sendObject(patternSelectedNetObject);


    }

    private void sendObject(NetObject object){
        try {
            out.reset();
            out.writeObject(object);
            out.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void moveDie(int diePosition, int x, int y) {
        if(isYourTurn(username)) {
            if (!isActionMoveDone()) {
                MoveDieNetObject moveDieNetObject = new MoveDieNetObject(username, diePosition, x, y);
                sendObject(moveDieNetObject);
            } else {
                onExceptionReceived("MoveAlreadydone", "You have already place a die !");
                notifyYourTurn(getClientGame());
            }
        }else{
            onExceptionReceived("NotYourTurn","It's not your turn !");
            notifyYourTurn(getClientGame());
        }
    }

    @Override
    public void useToolCard(int toolCardNumber, ArrayList<Integer> params) {
        if(isYourTurn(username)) {
            if (!isActionToolCardDone()) {

                if((getClientGame().getToolCards().get(toolCardNumber-1).getNumber()==6 || getClientGame().getToolCards().get(toolCardNumber-1).getNumber()==11)&& isActionMoveDone()){
                    notifyExceptionLaunched("IlligalAction", "You cannot use this toolcard now !");
                    notifyYourTurn(getClientGame());
                    return;
                }

                UseToolCardNetObject toolCardNetObject = new UseToolCardNetObject(getUsername(),toolCardNumber,params);
                sendObject(toolCardNetObject);
            } else {
                onExceptionReceived("ToolCardAlreadyUsed", "You have already used a toolcard !");
                notifyYourTurn(getClientGame());
            }
        }else{
            onExceptionReceived("NotYourTurn","It's not your turn !");
            notifyYourTurn(getClientGame());
        }
    }

    @Override
    public void endTool11(int dieNumber) {
        if(isYourTurn(username)){
            setActionToolCardDone(true);
            sendObject(new DieTool11ResponseObject(dieNumber));
        }
    }

    @Override
    public void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa) throws Exception {
        // ----- SIMO -----
        setPrivateObjectiveCard(pr);
        notifySetUp(pr,pu,pa);
    }

    @Override
    public void onSuccessfullLogin(String username) {
        // ----- SIMO -----
        this.username=username;
        setUsername(username);
        notifySuccessfullLogin(username);
        setLogged(true);

    }

    @Override
    public void onSuccessfullSignIn(String username) {
        notifySuccessfullSignIn(username);
    }

    @Override
    public void onExceptionReceived(String exceptionName, String errorMessage) {
        notifyExceptionLaunched(exceptionName,errorMessage);
        if (exceptionName.equals(IllegalActionException.class.getSimpleName())){
            notifyYourTurn(getClientGame());
        }
        //notifyClientGameReceived(getClientGame());
    }

    @Override
    public void onClientGameReceived(ClientGame cg) {
        if(getClientGame().getTurnNumber()!=cg.getTurnNumber()){
            setActionToolCardDone(false);
            setActionMoveDone(false);
        }
        setClientGame(cg);
        //clientGame=cg;
        notifyClientGameReceived(getClientGame());
    }

    @Override
    public void onResponseReceived(ResponseNetObject response) {
        String responseAction = response.getResponse();
        if(responseAction.equals(MessageHeaderEnum.MOVE.toString())){
            setActionMoveDone(true);
        }else if(responseAction.equals(MessageHeaderEnum.TOOLCARD.toString())){
            setActionToolCardDone(true);
        }
    }

    @Override
    public void onYourTurn(YourTurnNetObject turnNetObject) {
        if(username.equals(turnNetObject.getUsername())){
            notifyYourTurn(getClientGame());
        }
        else{
            notifyNotYourTurn();
        }
    }

    @Override
    public void endTurn(){
        if(isYourTurn(username)) {
            setActionToolCardDone(false);
            setActionMoveDone(false);
            sendObject(new EndTurnNetObject(username));
        }else{
        onExceptionReceived("NotYourTurn","It's not your turn !");
        }
    }

    private boolean isYourTurn(String username){
        if(getClientGame().getActivePlayerUsername().equals(username)) {
            return true;
        }
        return false;
    }

    @Override
    public void onEndGame(EndGameNetObject endGameNetObject) {
        notifyEndGame(endGameNetObject.getLeaderboard(), endGameNetObject.getScores());
    }

    @Override
    public void onForcedPatternChoice(ForcedGameStartedNetObject gameStartedNetObject) {

        this.username=gameStartedNetObject.getUsername();
        setUsername(username);
        setPrivateObjectiveCard(gameStartedNetObject.getPrivateObjectiveCard());

        notifyForcedPatternChoice();
    }

    @Override
    public void onDieTool11(DieTool11NetObject dieTool11NetObject) {
        notifyDieTool11(dieTool11NetObject.getD());
    }

    @Override
    public String getUsername() {
        return username;
    }

}
