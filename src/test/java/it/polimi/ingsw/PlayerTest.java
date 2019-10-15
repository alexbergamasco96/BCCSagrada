package it.polimi.ingsw;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    GsonReader gr = new GsonReader();

    final static int CONST4 = 4;
    final static int CONST6 = 6;
    final static int CONST25 = 25;

    final static String P1= "Fede";

    @Test
    public void PlayerTest(){
        Player player = new Player(P1,null);

        player.setToken(5);

        player.setPrivateObjectiveCard(new PrivateObjectiveCard("","",ColorANSI.ANSI_RED));

        Pattern p = (Pattern)gr.readObject("src/main/resources/json/pattern/23.json",Pattern.class);

        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(CONST6);

        try {
            p.placeDie(d,1,0);
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }

        player.setPattern(p);
        player.calculatePoint();

        assertEquals(0,player.getPoint());

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

        p.dump();

        player.calculatePoint();

        assertEquals(CONST25,player.getPoint());



    }

}
