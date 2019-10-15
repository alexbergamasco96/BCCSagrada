package it.polimi.ingsw.client.controller.SocketClient;

import it.polimi.ingsw.netObject.NetObject;
import it.polimi.ingsw.netObject.response.ResponseNetObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Receiver extends Thread {

    private ResponseObjectParser parser;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public Receiver(ObjectInputStream socketIn, ObjectOutputStream socketOut, ResponseObjectParser parser){
        this.parser=parser;
        this.socketIn=socketIn;
        this.socketOut=socketOut;
        this.start();
    }

    public void run(){

        while(true){
            NetObject obj = null;
            try {
                obj = null;
                obj = (NetObject) socketIn.readObject();
                parser.parse(obj);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}
