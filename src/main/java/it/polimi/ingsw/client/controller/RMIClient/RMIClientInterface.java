package it.polimi.ingsw.client.controller.RMIClient;


import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIClientInterface extends Remote {


    public void onSendSetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Integer> pa) throws RemoteException;

    public void onClientGameReceived(ClientGame clientGame) throws RemoteException;

    public void onMoveAccepted() throws RemoteException;

    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) throws RemoteException;

    public void onForcedPatternChoice(String username, PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu) throws RemoteException;

    public void onDieTool11Received(Die die) throws RemoteException ;
}
