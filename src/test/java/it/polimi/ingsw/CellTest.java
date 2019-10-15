package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.utility.ColorANSI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {

    final static int CONST1 = 3;
    final static int CONST2 = 5;

    final static int NOCONST =0;

    @Test
    void CellTest(){




        Cell c = new Cell(null,NOCONST);

        Die d = new Die(ColorANSI.ANSI_RED);
        d.roll();


        //place die in a no costrint cell

        assertEquals(true,c.placeDie(d));

        //place two dice in a cell

        Die d2 = new Die(ColorANSI.ANSI_BLUE);
        d2.setNumber(CONST1);

        assertEquals(false,c.placeDie(d2));
        c.freeCell();

        //place a blu die in a red cell

        c.setColorConstraint(ColorANSI.ANSI_RED);
        assertEquals(false,c.placeDie(d2));
        c.freeCell();

        //place a 3 die in a 5 cell

        c.setNumberConstraint(CONST2);
        assertEquals(false,c.placeDie(d2));

    }

}
