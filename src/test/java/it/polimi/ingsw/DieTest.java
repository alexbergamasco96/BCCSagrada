package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.utility.ColorANSI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DieTest {

    @Test
    void DieTest(){
        Die d = new Die(ColorANSI.ANSI_RED);
        assertEquals(ColorANSI.ANSI_RED,d.getColor());
    }

}
