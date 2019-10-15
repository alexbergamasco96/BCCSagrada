package it.polimi.ingsw.server.controller.RMI;



import it.polimi.ingsw.client.controller.RMIClient.RMIClientInterface;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Pattern;

import java.rmi.*;
import java.util.ArrayList;

public interface RMIServerInterface extends Remote {



    public void login(String username, String password, RMIClientInterface rmiClientInterface) throws RemoteException, AlreadyLoggedException, WrongPasswordException, NoUserException, GameFullException, GameStartedException, PlayerRiconnectionException;


    public void signIn(String username, String password) throws RemoteException, AlreadyUsedUsernameException;

    /*//Insert Exceptions
    public void doAction(String username, String action);*/

    /*public void moveDie(Integer diePosition, Integer rowCell, Integer columnCell );*/

    public void onPatternSelected(String username, Pattern p) throws RemoteException;


    public void onNotifyPatternArrived(String username) throws RemoteException;

    public void onMoveDie(String username, int reserveIndex, int x, int y) throws RemoteException, IllegalActionException;

    public void onEndTurn(String username) throws RemoteException;

    public void onUseToolCard(String username, int toolcardNumber, ArrayList<Integer> params) throws RemoteException, IllegalActionException, WrongResources;

    public void onEndTool11(int dieNumber) throws RemoteException ;
}
