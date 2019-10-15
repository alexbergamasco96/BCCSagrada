package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.utility.ColorANSI;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Credit to www.kenney.nl for dice images and dice sounds
 * Credit to Spring from www.opengameart.org for background sound
*/

public class Start {

    private enum UI{

        CLI("1) CLI - Command Line Interface"),
        GUI("2) GUI - Graphical User Interface");

        private String ui;

        UI(String ui) {
            this.ui = ui;
        }

        @Override
        public String toString() {
            return ui;
        }

    }

    public static void main(String[] args) {

        InputStreamReader reader = new InputStreamReader (System.in);
        BufferedReader myInput = new BufferedReader (reader);
        String input = new String();

        System.out.println("\n\n\t|------------------------------------------|\n");

        System.out.println(ColorANSI.ANSI_RED.escape()+"\t _____  ___  _____ ______  ___ ______  ___  \n" +
                ColorANSI.ANSI_RED.escape()+"\t/  ___|/ _ \\|  __ \\| ___ \\/ _ \\|  _  \\/ _ \\ \n" +
                ColorANSI.ANSI_BLUE.escape()+"\t\\ `--./ /_\\ \\ |  \\/| |_/ / /_\\ \\ | | / /_\\ \\\n" +
                ColorANSI.ANSI_PURPLE.escape()+"\t `--. \\  _  | | __ |    /|  _  | | | |  _  |\n" +
                ColorANSI.ANSI_GREEN.escape()+"\t/\\__/ / | | | |_\\ \\| |\\ \\| | | | |/ /| | | |\n" +
                ColorANSI.ANSI_YELLOW.escape()+"\t\\____/\\_| |_/\\____/\\_| \\_\\_| |_/___/ \\_| |_/\n" +
                "                                            \n" +ColorANSI.RESET);

        System.out.println("\t|------------------------------------------|\n\n");

        do{



            System.out.println("Choose an interface:");

            for( UI ui : UI.values() ) {
                System.out.println(ui);
            }

            try {
                input = myInput.readLine();
            } catch (IOException e) {
                System.out.println ("Si è verificato un errore: " + e);
            }
        }while (!input.equals("1")&&!input.equals("2"));

        switch (input){
            case "1":
                (new CLI()).startCLI();
                break;
            case "2":
                Application.launch(GUI.class);
                break;
        }

    }

}
