package it.polimi.ingsw.server.controller.RMI;

import it.polimi.ingsw.client.controller.RMIClient.RMIClientInterface;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.Observable;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Pattern;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface, RMIClientHandlerObserver {


    private Registry registry;

    private GameController gameController;

    //1099 default port
    private int port;


    public RMIServer(int port, GameController gameController) throws RemoteException{

        //Default RMIport 1099
        this.port = port;
        this.gameController = gameController;
        startServer();


    }


    /**
     * starts RMI server
     * @throws ServerException
     */

    public void startServer() throws ServerException{

        try {

            this.registry = LocateRegistry.createRegistry(this.port);
            this.registry.bind("rmi_server", this);
            System.out.println("[System] RMIServer is ready.");
        }catch (Exception e) {
            System.out.println("[Sagrada] RMI Server failed: " + e);
        }

    }

    /**
     * tries to log client into the game looking into the db
     * @param username
     * @param password
     * @param rmiClientInterface
     * @throws RemoteException
     * @throws AlreadyLoggedException
     * @throws WrongPasswordException
     * @throws NoUserException
     * @throws GameFullException
     * @throws GameStartedException
     * @throws PlayerRiconnectionException
     */

    @Override
    public void login(String username, String password, RMIClientInterface rmiClientInterface) throws RemoteException, AlreadyLoggedException, WrongPasswordException, NoUserException, GameFullException, GameStartedException, PlayerRiconnectionException {

        RMIClientHandler rmiClientHandler = new RMIClientHandler(rmiClientInterface, username);
        rmiClientHandler.addObserver(this);
        gameController.Login(username , password, rmiClientHandler);


    }

    /**
     * saves new users into db
     * @param username
     * @param password
     * @throws RemoteException
     * @throws AlreadyUsedUsernameException
     */

    @Override
    public void signIn(String username, String password) throws RemoteException, AlreadyUsedUsernameException {

        gameController.signIn(username, password);

    }


    @Override
    public void onPatternSelected(String username, Pattern p) throws RemoteException{

        gameController.setPattern(username, p);

    }


    @Override
    public void onNotifyPatternArrived(String username) throws RemoteException{

    }

    @Override
    public void onEndTurn(String username) throws RemoteException{
        gameController.nextTurn(username);
    }

    @Override
    public void onMoveDie(String username, int reserveIndex, int x, int y) throws IllegalActionException {

        gameController.moveDie(username, reserveIndex, x, y);

    }

    //notified from ClientHandler
    @Override
    public void onDisconnection(String username) {
        gameController.disconnect(username);
    }

    @Override
    public void onUseToolCard(String username, int toolcardNumber, ArrayList<Integer> params) throws RemoteException, IllegalActionException, WrongResources {

        gameController.useToolCard(username, toolcardNumber, params);

    }

    @Override
    public void onEndTool11(int dieNumber) {
        gameController.endTool11(dieNumber);
    }
}
