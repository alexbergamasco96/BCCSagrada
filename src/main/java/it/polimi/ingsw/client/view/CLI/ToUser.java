package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.Client;
import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.toolcard.ToolCard;
import it.polimi.ingsw.utility.AnsiBackground;
import it.polimi.ingsw.utility.BestScore;
import it.polimi.ingsw.utility.ColorANSI;

import java.util.ArrayList;

public class ToUser {

    private final int LINE = 60;

    public void request(String s){

        System.out.println( ColorANSI.ANSI_YELLOW.escape() + s + ColorANSI.RESET);

    }

    public void notify(String s){

        System.out.println( ColorANSI.ANSI_GREEN.escape() + s + ColorANSI.RESET);

    }


    public void error(String s){

        System.out.println( ColorANSI.ANSI_RED.escape() + s + ColorANSI.RESET);

    }


    public void sendBoardGame(ClientGame clientGame, String username, PrivateObjectiveCard pr){

        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        sendLine();
        sendLine();
        System.out.println("\n");
        System.out.println("\n");
        System.out.println(ColorANSI.ANSI_BLUE.escape() + "----------------------------------------------"+ ColorANSI.RESET);
        System.out.println(ColorANSI.ANSI_BLUE.escape() + "----------||      ROUNDTRACK      ||----------" + ColorANSI.RESET);
        System.out.println(ColorANSI.ANSI_BLUE.escape() + "----------------------------------------------"+ ColorANSI.RESET);

        System.out.println("\n");

        String string;
        for(int i = 1; i < clientGame.getRoundNumber() ; i++){

            string = "ROUND  "+i+" : \t";
            for(Die d : clientGame.getRoundTrack().getTrack().get(i-1)){
                string += d.toString2();
                string += " ";
            }
            System.out.println(string);
        }

        sendSeparator();

        System.out.println(ColorANSI.ANSI_GREEN.escape() + "\t\t\tROUND CORRENTE:\t"+clientGame.getRoundNumber()+ColorANSI.RESET);
        sendSeparator();
        System.out.println(ColorANSI.ANSI_GREEN.escape() + "\t\t\tTURNO CORRENTE:\t"+clientGame.getTurnNumber() + ColorANSI.RESET);
        sendSeparator();


        int i;

        for( i = 0 ; i < clientGame.getPlayers().size() ; i++ ){

            if(clientGame.getPlayers().get(i).getUsername().equals(username)){
                break;
            }

        }

        System.out.println(ColorANSI.ANSI_RED.escape()+"\tOBIETTIVO PRIVATO\n" + ColorANSI.RESET + pr.getTargetColor().escape()+ pr.getName() + ColorANSI.RESET + ColorANSI.ANSI_YELLOW.escape()+" : " + pr.getDescription()+ColorANSI.RESET);

        sendSeparator();

        System.out.println(ColorANSI.ANSI_GREEN.escape()+"\tOBIETTIVI PUBBLICI : \n"+ColorANSI.RESET);

        for(int j = 0 ; j < clientGame.getPublicObjectiveCards().size() ; j++){
            System.out.println(ColorANSI.ANSI_GREEN.escape() + clientGame.getPublicObjectiveCards().get(j).getName() + " :  "+ColorANSI.RESET + ColorANSI.ANSI_YELLOW.escape() + clientGame.getPublicObjectiveCards().get(j).getDescription()+ColorANSI.RESET);
        }


        sendSeparator();

        //System.out.println("I PATTERN DEI GIOCATORI");
        //sendPatterns(clientGame);
        System.out.println("\tIL TUO PATTERN : ");
        System.out.println(clientGame.getPlayers().get(i).getPattern().toString());

        sendSeparator();

        System.out.println("\tPATTERN DEGLI ALTRI GIOCATORI : ");

        for(ClientPlayer p : clientGame.getPlayers()){
            if(!p.getUsername().equals(username)) {
                System.out.println("\t\t" + p.getUsername());
                System.out.println(p.getPattern().toString());
            }

        }

        sendSeparator();

        notify("----INFORMAZIONI----");

        System.out.println(ColorANSI.ANSI_GREEN.escape()+"TU (" + clientGame.getPlayers().get(i).getUsername() + ") HAI : " + clientGame.getPlayers().get(i).getToken() +" SEGNALINI\n"+ColorANSI.RESET);
        System.out.println("SEGNALINI DEGLI ALTRI GIOCATORI : ");
        for(ClientPlayer p : clientGame.getPlayers()){
            if(!p.getUsername().equals(username)) {
                System.out.println(p.getUsername() + " HA " + p.getToken() +" SEGNALINI");
            }

        }

        sendSeparator();

        System.out.println("\t\t\tRISERVA DI DADI");
        String reserve = "\t\t";
        for(Die d : clientGame.getReserve()){
            reserve += d.toString();
        }
        System.out.println(reserve);
        sendLine();



    }

    public void sendLine(){

        String s = "||";

        for(int i = 0; i < LINE ; i ++){

            s += "-";
        }

        s += "||";
        System.out.println(s);

    }

    public void sendSeparator(){

        String s = "";

        for(int i = 0; i < (LINE + 2) ; i ++){

            s += "*";
        }

        System.out.println(s);
    }

    public void sendReserve(ArrayList<Die> reserve){

        String s = "";

        for(Die d : reserve){

            s += d.toString();
            s += "\t";

        }

        s += "\n";

        for(int i = 1; i <= reserve.size() ; i ++){

            s += "["+i+"]";
            s += "\t";

        }

        System.out.println(s);


    }

    public void printToolCards(ClientGame clientGame){

        sendSeparator();
        sendSeparator();
        notify("ELENCO DELLE TOOLCARD: \n");

        for(ToolCard t : clientGame.getToolCards()){

            System.out.println(ColorANSI.ANSI_PURPLE.escape() + "\n\t"+t.getNumber()+")\t"+t.getName() + ColorANSI.RESET);
            System.out.println(ColorANSI.ANSI_YELLOW.escape() + "Descrizione :\t"+t.getDescription() + ColorANSI.RESET);
            System.out.println("Segnalini necessari per utilizzarla : \t" + ColorANSI.ANSI_RED.escape() + t.tokenToUse()+"\n" + ColorANSI.RESET);
            sendSeparator();

        }
        sendSeparator();

    }

    public void sendLeaderBoard(Leaderboard leaderboard){

        sendLine();
        sendSeparator();

        System.out.println( ColorANSI.ANSI_GREEN.escape() + "POSIZIONE\t\tPUNTI\t\tGIOCATORE" + ColorANSI.RESET);


        for( int i = 0, j ; i < leaderboard.getPlacements().size() ; i ++){

            j = i+1;

            System.out.println( ColorANSI.ANSI_GREEN.escape() + j + "\t\t" + leaderboard.getPlacements().get(i).getPoint() + "\t\t" + leaderboard.getPlacements().get(i).getName() + ColorANSI.RESET);

        }


        sendLine();
        sendSeparator();

    }

    public void sendPatterns(ClientGame clientGame){

        String s = "\t";

        for(ClientPlayer clientPlayer : clientGame.getPlayers()){

            s+=clientPlayer.getUsername().toUpperCase();
            s+="\t\t\t\t\t";
        }

        s+="\n";

        for(int i = 0; i < 4; i++){

            for(ClientPlayer clientPlayer : clientGame.getPlayers()){

                s+=clientPlayer.getPattern().getRow(i);
                s+="\t\t\t";
            }
            s+="\n";

        }
        s+="\n";

        System.out.println(s);


    }

    public void sendBestScores(ArrayList<BestScore> bestScores){

        for(BestScore bestScore : bestScores){

            System.out.println(bestScore.getBestScore() + "\t" + bestScore.getUsername());

        }

    }

    public void sendDescription(){

        System.out.println("\n");
        String title = ColorANSI.ANSI_RED.escape() +"S"+ ColorANSI.ANSI_YELLOW.escape() +"A"+ ColorANSI.ANSI_GREEN.escape() +"G"+ ColorANSI.ANSI_PURPLE.escape() +"R" + ColorANSI.ANSI_BLUE.escape() +"A"+ ColorANSI.ANSI_RED.escape() +"D"+ ColorANSI.ANSI_YELLOW.escape() +"A"+ ColorANSI.RESET;

        System.out.println(title);

        System.out.println(ColorANSI.ANSI_GREEN.escape() +"\tSiete artisti in competizione ta loro per creare la vetrata più spettacolare della Sagrada Familia." +ColorANSI.RESET);
        System.out.println(ColorANSI.ANSI_GREEN.escape() +"\tI vostri frammenti di vetro sono rappresentati da dadi, caratterizzati da un colore e da una sfumatura che è indicata dal loro valore numerico." +ColorANSI.RESET);

        System.out.println("\n");
    }



}
