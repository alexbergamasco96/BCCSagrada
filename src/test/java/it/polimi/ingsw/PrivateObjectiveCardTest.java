package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.utility.ColorANSI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrivateObjectiveCardTest {

    final static int ROW = 4;
    final static int COLUMN = 5;

    final static int CONSTANT6 =6;
    final static int CONSTANT3 =3;



    @Test
    void PrivateObjectiveCardTest(){

        Pattern p1 = new Pattern();
        PrivateObjectiveCard pov = new PrivateObjectiveCard("Red Shades","Somma red", ColorANSI.ANSI_RED);

        assertEquals(0, pov.calculateCardPoints(p1));

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                //p.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(CONSTANT6);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT6);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(CONSTANT6*ROW*COLUMN, pov.calculateCardPoints(p1));


        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                //p2.getCell(i, j).setNumberConstraint(6);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT3);
                p2.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(CONSTANT3*ROW*COLUMN, pov.calculateCardPoints(p2));

    }

}
