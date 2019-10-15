package it.polimi.ingsw.client.controller.RMIClient;

import it.polimi.ingsw.client.controller.Client;
import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.SocketClient.PatternNumberParser;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.controller.RMI.RMIServerInterface;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIClient extends Client implements RMIClientInterface {


    private RMIServerInterface server;

    private String username;

    private ArrayList<Pattern> patterns = new ArrayList<Pattern>();

    public  RMIClient(String address, int port){
        super(address, port);
        setLogged(false);
    }



    public void connectToServer() throws Exception{
        Registry registry;

            registry = LocateRegistry.getRegistry(getIp(), getPort());
            server = (RMIServerInterface)registry.lookup("rmi_server");
            UnicastRemoteObject.exportObject(this, 0);
            //registry.rebind("RMIClientInterface", this);

    }

    public void login(String username, String password) throws AlreadyLoggedException, GameFullException, WrongPasswordException, NoUserException, GameStartedException, PlayerRiconnectionException{
            try{
                server.login(username, password, this);
                setLogged(true);
                notifySuccessfullLogin(username); //notify the Controller

            }catch(RemoteException e){
                e.printStackTrace();
            }catch(AlreadyLoggedException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());

            }catch(GameFullException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
            }catch(WrongPasswordException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
            }catch(NoUserException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
            }catch(GameStartedException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
            }catch (PlayerRiconnectionException e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
            }
            this.username = username;


    }

    public void signIn(String username, String password) {
        try {
            server.signIn(username, password);
            notifySuccessfullSignIn(username);
        } catch (RemoteException e) {
            notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
        } catch (AlreadyUsedUsernameException e){
            notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
        }


    }


    @Override
    public void endTool11(int dieNumber) {
        try {
            server.onEndTool11(dieNumber);
            setActionToolCardDone(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSendSetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Integer> pa) throws RemoteException{



        patterns = new PatternNumberParser().parse(pa.get(0), pa.get(1), pa.get(2), pa.get(3));

        getClientGame().setPublicObjectiveCards(pu);
        setPrivateObjectiveCard(pr);



        notifySetUp(getPrivateObjectiveCard() , getClientGame().getPublicObjectiveCards() , patterns);


    }


    @Override
    public void patternSelected(Pattern pattern) {

        try{
            server.onPatternSelected(username, pattern);
        }catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveDie(int diePosition, int x, int y) {
        if(!isActionMoveDone()) {
            try {
                server.onMoveDie(username, diePosition, x, y);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                notifyYourTurn(getClientGame());

            }
        } else{
            notifyExceptionLaunched("MoveAlreadydone", "You have already place a die !");
            notifyYourTurn(getClientGame());

        }

    }

    @Override
    public void useToolCard(int toolCardNumber, ArrayList<Integer> params) {

        if(!isActionToolCardDone()) {
            if((getClientGame().getToolCards().get(toolCardNumber-1).getNumber()==6 || getClientGame().getToolCards().get(toolCardNumber-1).getNumber()==11)&& isActionMoveDone()){
                notifyExceptionLaunched("IlligalAction", "You cannot use this toolcard now !");
                notifyYourTurn(getClientGame());
                return;
            }
            try {
                server.onUseToolCard(username, toolCardNumber, params);
                getClientGame().getToolCards().get(toolCardNumber-1).setUsed();
                setActionToolCardDone(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                notifyYourTurn(getClientGame());
            } catch (WrongResources e){
                notifyExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                notifyYourTurn(getClientGame());
            }
        } else{
            notifyExceptionLaunched("Action already done", "You have already use a toolcard!");
            notifyYourTurn(getClientGame());
        }

    }

    @Override
    public void onMoveAccepted() {
        setActionMoveDone(true);
    }

    @Override
    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) throws RemoteException {

        notifyEndGame(leaderboard, scores);

    }

    @Override
    public void onClientGameReceived(ClientGame clientGame) throws RemoteException {
        if(getClientGame().getTurnNumber()!=clientGame.getTurnNumber()){
            setActionToolCardDone(false);
            setActionMoveDone(false);
        }
        setClientGame(clientGame);
        notifyClientGameReceived(getClientGame());


    }

    @Override
    public void endTurn(){
        try{
            setActionMoveDone(false);
            setActionToolCardDone(false);
            server.onEndTurn(username);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onForcedPatternChoice(String username, PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu) throws RemoteException {
        setUsername(username);
        setPrivateObjectiveCard(pr);

        notifyForcedPatternChoice();
    }

    @Override
    public void onDieTool11Received(Die die) {
        notifyDieTool11(die);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
