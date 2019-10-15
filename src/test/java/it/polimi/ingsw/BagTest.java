package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.utility.GsonReader;
import it.polimi.ingsw.utility.GsonWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagTest {

    final static int FULL_BAG_DIE = 90;


    @Test
    void BagTest(){

        Bag b = new Bag();
        assertEquals(FULL_BAG_DIE,b.getSize());
        b.extractReserve(FULL_BAG_DIE);
        assertEquals(0,b.getSize());
    }

}
