package it.polimi.ingsw;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    GsonReader gr = new GsonReader();

    final static int MAX_ROUND = 10;

    final static int CONST4 = 4;
    final static int CONST6 = 6;
    final static int NUM_TOKEN = 15;

    final static String P1= "Fede";
    final static String P2= "Simo";

    @Test
    public void Gametest(){
        Game game = new Game();
        Player p1 = new Player();
        p1.setNickname(P1);
        p1.setColor(ColorANSI.ANSI_RED);
        Player p2 = new Player();
        p2.setNickname(P2);
        p2.setColor(ColorANSI.ANSI_PURPLE);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        game.setUp(players);
        game.extractReserve(players.size());

        for(int i=0;i<MAX_ROUND-1;i++){
            assertEquals(true,game.nextRound());
            assertEquals(true,game.nextTurn());
            game.extractReserve(players.size());
        }
        assertEquals(true,game.nextTurn());
        assertEquals(true,game.nextTurn());



        //over last turn
        assertEquals(false,game.nextTurn());
        //over last round
        assertEquals(false,game.nextRound());


        p1.setToken(NUM_TOKEN);

        p1.setPrivateObjectiveCard(new PrivateObjectiveCard("","",ColorANSI.ANSI_RED));

        Pattern p = (Pattern)gr.readObject("src/main/resources/json/pattern/23.json",Pattern.class);

        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(CONST6);

        try {
            p.placeDie(d,1,0);
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }


        p1.setPattern(p);


        Die d2  = new Die(ColorANSI.ANSI_RED);
        d2.setNumber(CONST4);


        try {
            p.placeDie(d,0,1);
            p.placeDie(d,2,1);
            p.placeDie(d2,3,2);
            p.placeDie(d,4,3);
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }


        game.calculatePoint();


    }

}
