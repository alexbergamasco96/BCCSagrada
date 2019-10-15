package it.polimi.ingsw.server.controller.socket;

import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.netObject.*;
import it.polimi.ingsw.netObject.response.ResponseNetObject;
import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Created by cisla73 on 09/05/18
 */

public class SocketClientHandler implements ClientHandler,Runnable, Observable<SocketClientHandlerObserver>, SocketServerObserver {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private ArrayList<SocketClientHandlerObserver> observers;
    private String username;
    private boolean connected;

    public SocketClientHandler(Socket socket) {
        this.socket = socket;
        this.observers= new ArrayList<SocketClientHandlerObserver>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;

            while (connected) {
                
                // message in input from client
                try {
                    Object obj = in.readObject();

                    //notify server of actionObject
                    notifyObjectReceived((NetObject)obj);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException e){
                    e.printStackTrace();
                    in.close();
                    out.close();
                    socket.close();
                    System.out.println("CLIENT DISCONNESSO "+username);
                    connected=false;
                    notifyDisconnection(username);
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                in.close();
                out.close();
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }


            System.err.println(e.getMessage());
            System.out.println("CLIENT DISCONNESSO "+username);
            connected=false;
            notifyDisconnection(username);
        }

    }

    /**
     * notifies serverSocket of a new object from client
     * @param netObject received object
     */


    public void notifyObjectReceived(NetObject netObject){
        for (SocketClientHandlerObserver obs:observers) {
            obs.onActionObjectReceived(netObject, this);
        }
    }


    /**
     * sends response to client
     * @param response net object containing the response
     */

    public void sendResponse(ResponseNetObject response){

            sendObject(response);

    }

    /**
     * sends set up (private, public cards and four patterns) to client
     * @param pr private card
     * @param pu public cards
     * @param pa four patterns
     */

    @Override
    public void sendSetUp(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Integer> pa) {

            sendObject(new SetupNetObject(MessageHeaderEnum.SETUP.toString(),pr,pu,pa));

    }

    /**
     * sends clientGame to client
     * @param clientGame
     */

    @Override
    public void sendClientGame(ClientGame clientGame) {

            //System.out.println("Invio "+clientGame+clientGame.getReserve()+" a "+username);
            ClientGameNetObject clientGameNetObject = new ClientGameNetObject(MessageHeaderEnum.CLIENTGAME.toString(),clientGame);
            //System.out.println(clientGameNetObject+" : "+clientGameNetObject.getClientGame().getReserve()+" : "+clientGame.getActivePlayerUsername());
            sendObject(clientGameNetObject);
    }

    /**
     * sends end game (leaderBoard with game scores and ten best scores) to client
     * @param leaderboard game scores
     * @param scores ten all time best scores
     */

    @Override
    public void sendEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) {

            sendObject(new EndGameNetObject(leaderboard,scores));

    }

    /**
     * sends forced notification to client on reconnection
     * @param username player username
     * @param pr private card
     * @param pu public cards
     */

    @Override
    public void sendForcedGameStarted(String username, PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu) {

            sendObject(new ForcedGameStartedNetObject(username,pr,pu));

    }


    //sends first object of toolCard 11

    @Override
    public void sendDieTool11(Die die) {
        sendObject(new DieTool11NetObject(die));
    }

    @Override
    public void onResponseReceived(String response) {

    }

    /**
     * sends object on output stream to client
     * @param obj net object
     */

   private void sendObject(Object obj){
       try {
           out.reset();
           out.writeObject(obj);
           out.flush();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    /**
     * notifies socketServer of client disconnection
     * @param username player username
     */

   public void notifyDisconnection(String username){
       for(SocketClientHandlerObserver obs:observers){
           obs.onDisconnection(username);
       }
   }


    @Override
    public void addObserver(SocketClientHandlerObserver observer) {
        observers.add(observer);
        System.out.println("Aggiunto nuovo observer");
    }

    @Override
    public void removeObserver(SocketClientHandlerObserver observer) {
        observers.remove(observer);
    }


}
