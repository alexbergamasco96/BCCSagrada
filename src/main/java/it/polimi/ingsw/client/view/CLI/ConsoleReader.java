package it.polimi.ingsw.client.view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleReader extends Thread{



    private CLI cli;

    private Scanner scan = new Scanner(System.in);


    private boolean pause;


    public ConsoleReader(CLI cli){

        this.cli = cli;

    }

    @Override
    public void run(){

        while(scan.hasNextLine()){

            try{

                String input = scan.nextLine();


                sendInput(input);

            }catch (Exception e){
                scan.close();
            }

        }



        scan.close();


    }


    public void sendInput(String input) throws InterruptedException{

        cli.getInput(input.toLowerCase());
    }


    public synchronized void stopInput(){

        while(pause){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

    }




}
