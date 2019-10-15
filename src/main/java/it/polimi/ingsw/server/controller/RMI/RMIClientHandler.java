package it.polimi.ingsw.server.controller.RMI;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.RMIClient.RMIClientInterface;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.controller.ClientHandler;
import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.controller.Observable;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClientHandler implements ClientHandler, Observable<RMIClientHandlerObserver> {



    //reference to client
    private RMIClientInterface rmiClientInterface;

    private String username;

    private ArrayList<RMIClientHandlerObserver> observers;


    //Constructor
    public RMIClientHandler(RMIClientInterface r, String username) {

        this.rmiClientInterface = r;
        this.username = username;
        this.observers=new ArrayList<RMIClientHandlerObserver>();
        //ping();


    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public void sendSetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Integer> pa) {
        try{
            rmiClientInterface.onSendSetUp(pr, pu, pa);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }




    public void sendClientGame(ClientGame clientGame){

        try{
            rmiClientInterface.onClientGameReceived(clientGame);
        }catch(RemoteException e){
            e.printStackTrace();
            notifyDisconnection(username);
        }

    }

    @Override
    public void sendResponse(ResponseNetObject actionResponse) {
        if(actionResponse.getResponse().equals(MessageHeaderEnum.MOVE.toString())) {
            try {
                rmiClientInterface.onMoveAccepted();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) {

        try {
            System.out.println("leaderboard su rmiclienthandler");
            leaderboard.dump();
            rmiClientInterface.onEndGame(leaderboard, scores);
        }catch(RemoteException e){
            e.printStackTrace();
        }


    }

    @Override
    public void sendForcedGameStarted(String username, PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu) {
        try {
            rmiClientInterface.onForcedPatternChoice(username,pr,pu);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void notifyDisconnection(String username){
        for(RMIClientHandlerObserver observer:observers){
            observer.onDisconnection(username);
        }
    }

    @Override
    public void sendDieTool11(Die die) {
        try {
            rmiClientInterface.onDieTool11Received(die);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(RMIClientHandlerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(RMIClientHandlerObserver observer) {
        observers.remove(observer);
    }
}
