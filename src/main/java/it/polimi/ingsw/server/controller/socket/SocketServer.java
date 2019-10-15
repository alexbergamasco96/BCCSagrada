package it.polimi.ingsw.server.controller.socket;


import it.polimi.ingsw.client.controller.SocketClient.ResponseHeaderEnum;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.netObject.*;
import it.polimi.ingsw.netObject.response.DieTool11ResponseObject;
import it.polimi.ingsw.netObject.response.ExceptionResponseObject;
import it.polimi.ingsw.netObject.response.LoginResponseObject;
import it.polimi.ingsw.netObject.response.SignInResponseObject;
import it.polimi.ingsw.server.controller.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Created by cisla73 on 09/05/18
*/

public class SocketServer extends Thread implements SocketClientHandlerObserver, Observable<SocketServerObserver> {

    private int port;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private ArrayList<SocketServerObserver> observers;
    private GameController gameController;


    public SocketServer(int port, GameController gameController) {
        this.port = port;
        this.gameController=gameController;
        this.observers= new ArrayList<SocketServerObserver>();

    }

    public void run(){
        executor = Executors.newCachedThreadPool();



        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server in ascolto!");


        while ( true ) {
            try {
                Socket socket = serverSocket.accept();
                SocketClientHandler clientHandler = new SocketClientHandler(socket);
                clientHandler.addObserver(this);

                executor.submit(clientHandler);
                System.out.println("Connessione nuovo client");
            } catch (IOException e) {
                break; // entrerei qui se serverSocket venisse chiuso }
            }
            //executor.shutdown();

        }

    }


    /**
     * handles action object notification parsing the object and calling linked gamecontroller action
     * @param netObject received net object
     * @param socketClientHandler client handler that notifies
     */

    @Override
    public void onActionObjectReceived(NetObject netObject, SocketClientHandler socketClientHandler) {

        System.out.println("Ricevuto oggetto : "+netObject.getHeader());

        if(netObject.getHeader().equals(MessageHeaderEnum.LOGIN.toString())){
            LoginNetObject parsedObject = (LoginNetObject) netObject;
            try {
                if(gameController.Login(parsedObject.getUsername(),parsedObject.getPassword(),socketClientHandler)) {
                    socketClientHandler.setUsername(parsedObject.getUsername());
                    socketClientHandler.sendResponse(new LoginResponseObject(parsedObject.getHeader(), ResponseHeaderEnum.OK.toString(), parsedObject.getUsername()));
                }
            } catch (AlreadyLoggedException e) {
                e.printStackTrace();
                //socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
                sendExceptionResponse(e,socketClientHandler);
            } catch (WrongPasswordException e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
                //socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
            } catch (NoUserException e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
                //socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
            } catch (GameFullException e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
                //socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
            } catch (GameStartedException e) {
                e.printStackTrace();
                if(gameController.getClientGame().getPlayerFromName(e.getUsername())!=null){
                    socketClientHandler.sendClientGame(gameController.getClientGame());
                    return;
                }
                sendExceptionResponse(e,socketClientHandler);
                //socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(), e.getMessage(), e.getClass().getSimpleName()));
            }
        }
        else if(netObject.getHeader().equals(MessageHeaderEnum.SIGNIN.toString())){
            SignInNetObject parsedObject = (SignInNetObject)netObject;
            try {
                gameController.signIn(parsedObject.getUsername(),parsedObject.getPassword());
                socketClientHandler.sendResponse(new SignInResponseObject(parsedObject.getHeader().toString(),parsedObject.getUsername()));
            } catch (AlreadyUsedUsernameException e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
            }
        }
        else if(netObject.getHeader().equals(MessageHeaderEnum.PATTERN.toString())){
            PatternSelectedNetObject parsedObject = (PatternSelectedNetObject)netObject;
            gameController.setPattern(parsedObject.getUsername(),parsedObject.getPatternSelected());
        }
        else if(netObject.getHeader().equals(MessageHeaderEnum.MOVE.toString())){
            MoveDieNetObject parsedObject = (MoveDieNetObject)netObject;
            try {
                gameController.moveDie(parsedObject.getUsername(),parsedObject.getDiePosition(),parsedObject.getX(),parsedObject.getY());
            } catch (IllegalActionException e) {
                socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
                //socketClientHandler.sendClientGame(gameController.getClientGame());
                //socketClientHandler.sendYourTurn();
                e.printStackTrace();
            }
        }else if(netObject.getHeader().equals(MessageHeaderEnum.TOOLCARD.toString())){
            UseToolCardNetObject parsedObject = (UseToolCardNetObject) netObject;
            try {
                gameController.useToolCard(parsedObject.getUsername(),parsedObject.getToolCardNumber(),parsedObject.getParams());
                System.out.println("TOOLCARD ESEGUITA CON SUCCESSO");
            } catch (IllegalActionException e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
            } catch (WrongResources e) {
                e.printStackTrace();
                sendExceptionResponse(e,socketClientHandler);
            }
        }
        else if(netObject.getHeader().equals(MessageHeaderEnum.ENDTURN.toString())){
            EndTurnNetObject parsedObject = (EndTurnNetObject) netObject;
            gameController.nextTurn(parsedObject.getUsername());
        }
        else if(netObject.getHeader().equals(MessageHeaderEnum.DIETOOL11.toString())){
            DieTool11ResponseObject parsedObject = (DieTool11ResponseObject) netObject;
            gameController.endTool11(parsedObject.getDieNumber());
        }

    }

    @Override
    public void onDisconnection(String username) {
        gameController.disconnect(username);
    }

    /**
     * sends exception object to respective clientHandler
     * @param e exception
     * @param socketClientHandler who should be notified
     */

    private void sendExceptionResponse(Exception e, SocketClientHandler socketClientHandler){
        socketClientHandler.sendResponse(new ExceptionResponseObject(ResponseHeaderEnum.EXCEPTION.toString(),e.getMessage(),e.getClass().getSimpleName()));
    }


    @Override
    public void addObserver(SocketServerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SocketServerObserver observer) {
        observers.remove(observer);
    }



}
