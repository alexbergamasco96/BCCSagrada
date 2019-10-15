package it.polimi.ingsw;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternTest {


    GsonReader gr = new GsonReader();


    final static int CONST3 = 3;

    @Test
    public void PatternTest(){


        Pattern p = (Pattern)gr.readObject("src/main/resources/json/pattern/23.json",Pattern.class);
        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(CONST3);

        //p.dump();

        try{
            p.placeDie(d,4,0);
        }
        catch (IllegalActionException e){
            assertEquals("Mossa non valida !",e.getMessage());
        }
        p.dump();
        try{
            p.placeDie(d,0,0);
        }
        catch (IllegalActionException e){
            assertEquals("Mossa non valida !",e.getMessage());
        }

        try{
            p.placeDie(d,1,0);
        }
        catch (IllegalActionException e){
            assertEquals("",e.getMessage());
        }
        p.dump();


    }

}
