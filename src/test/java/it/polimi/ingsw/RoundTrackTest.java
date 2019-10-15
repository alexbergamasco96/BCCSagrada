package it.polimi.ingsw;

import it.polimi.ingsw.exception.OutOfRoundTrackException;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.RoundTrack;
import it.polimi.ingsw.utility.ColorANSI;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTrackTest {

    final static int ONE =1;
    final static int TWO =2;
    final static int THREE =3;
    final static int FOUR =4;
    final static int FIVE =5;
    final static int SIX =6;
    final static int SEVEN =7;
    final static int EIGHT =8;
    final static int NINE =9;
    final static int TEN =10;
    final static int ELEVEN =11;
    
    @Test
    void RoundTrackTest(){
        Die d1 = new Die(ColorANSI.ANSI_GREEN);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(TWO);
        Die d3 = new Die(ColorANSI.ANSI_GREEN);
        d3.setNumber(THREE);
        Die d4 = new Die(ColorANSI.ANSI_GREEN);
        d4.setNumber(FOUR);
        Die d5 = new Die(ColorANSI.ANSI_GREEN);
        d5.setNumber(FIVE);
        Die d6 = new Die(ColorANSI.ANSI_GREEN);
        d6.setNumber(SIX);
        Die d7 = new Die(ColorANSI.ANSI_GREEN);
        d7.setNumber(ONE);
        Die d8 = new Die(ColorANSI.ANSI_GREEN);
        d8.setNumber(TWO);
        Die d9 = new Die(ColorANSI.ANSI_GREEN);
        d9.setNumber(THREE);
        Die d10 = new Die(ColorANSI.ANSI_GREEN);
        d10.setNumber(FOUR);

        RoundTrack roundTrack = new RoundTrack();


        try {
            roundTrack.insertDie(ONE,d1);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(TWO,d2);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(THREE,d3);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(FOUR,d4);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(FIVE,d5);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(SIX,d6);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(SEVEN,d7);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(EIGHT,d8);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(NINE,d9);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(TEN,d10);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }
        try {
            roundTrack.insertDie(TEN,d1);
        } catch (OutOfRoundTrackException e) {
            assertEquals("",e.getMessage());
        }

        try {
            roundTrack.insertDie(ELEVEN,d1);
        } catch (OutOfRoundTrackException e) {
            assertEquals("Stai posizionando o cercando di prendere un dado oltre i 10 round del RoundTrack",e.getMessage());
        }

        assertEquals(d1,roundTrack.getDice(ONE).get(0));

        ArrayList<Die> expected = new ArrayList<Die>();
        expected.add(d10);
        expected.add(d1);

        assertEquals(expected,roundTrack.getDice(TEN));

    }


}
