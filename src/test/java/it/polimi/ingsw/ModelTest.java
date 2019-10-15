package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.model.*;
/*<<<<<<< HEAD*/
import it.polimi.ingsw.server.model.publicObjectiveCards.*;
/*=======*/
import it.polimi.ingsw.server.model.publicObjectiveCards.ColumnColorVariety;
import it.polimi.ingsw.server.model.publicObjectiveCards.ColumnShadeVariety;
import it.polimi.ingsw.server.model.publicObjectiveCards.RowColorVariety;
import it.polimi.ingsw.server.model.publicObjectiveCards.RowShadeVariety;
/*>>>>>>> a5c816d183e33fc8b008672a0e5d341550acc0de*/
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;
import it.polimi.ingsw.utility.GsonWriter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class ModelTest {

    final static int FULL_BAG_DIE = 90;

    final static int ROW = 4;
    final static int COLUMN = 5;

    final static int NOCONST =0;


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
    
    final static String P1 = "Fede";
    final static String P2 = "Simo";
    final static String P3 = "Alex";
            

    GsonWriter gw = new GsonWriter();
    GsonReader gr = new GsonReader();

    @Test
    void DieTest(){
        Die d = new Die(ColorANSI.ANSI_RED);
        assertEquals(ColorANSI.ANSI_RED,d.getColor());
    }

    @Test
    void BagTest(){

        Bag b = new Bag();
        assertEquals(FULL_BAG_DIE,b.getSize());
        b.extractReserve(FULL_BAG_DIE);
        assertEquals(0,b.getSize());
    }

    @Test
    void CellTest(){

        Cell c = new Cell(null,NOCONST);

        Die d = new Die(ColorANSI.ANSI_RED);
        d.roll();


        //place die in a no costrint cell

        assertEquals(true,c.placeDie(d));

        //place two dice in a cell

        Die d2 = new Die(ColorANSI.ANSI_BLUE);
        d2.setNumber(THREE);

        assertEquals(false,c.placeDie(d2));
        c.freeCell();

        //place a blu die in a red cell

        c.setColorConstraint(ColorANSI.ANSI_RED);
        assertEquals(false,c.placeDie(d2));
        c.freeCell();

        //place a 3 die in a FIVE cell

        c.setNumberConstraint(FIVE);
        assertEquals(false,c.placeDie(d2));

    }

    @Test
    public void PatternTest(){
        Pattern p = (Pattern)gr.readObject("src/main/resources/json/pattern/23.json",Pattern.class);
        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(THREE);


        try{
            p.placeDie(d,FOUR,0);
        }
        catch (IllegalActionException e){
            assertEquals("Mossa non valida !",e.getMessage());
        }

        try{
            p.placeDie(d,0,0);
        }
        catch (IllegalActionException e){
            assertEquals("Mossa non valida !",e.getMessage());
        }

        try{
            p.placeDie(d,ONE,0);
        }
        catch (IllegalActionException e){
            assertEquals("",e.getMessage());
        }
        


    }




    @Test
    void PrivateObjectiveCardTest(){

        Pattern p1 = new Pattern();
        PrivateObjectiveCard pov = new PrivateObjectiveCard("Red Shades","Somma red",ColorANSI.ANSI_RED);

        assertEquals(0, pov.calculateCardPoints(p1));

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                //p.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(SIX);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(SIX);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(SIX *ROW*COLUMN, pov.calculateCardPoints(p1));


        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                //p2.getCell(i, j).setNumberConstraint(SIX);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p2.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(THREE *ROW*COLUMN, pov.calculateCardPoints(p2));

    }


    /*----STRATEGY TEST----*/

    @Test
    void RowColorVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard cr = new RowColorVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(SIX);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, cr.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
            //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
            p2.getCell(i, j).setNumberConstraint(THREE);
            if(j == 0){
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p2.getCell(i, j).placeDie(d);
            }
            if(j==ONE) {
                Die d = new Die(ColorANSI.ANSI_GREEN);
                d.setNumber(THREE);
                p2.getCell(i, j).placeDie(d);
            }
            if(j==TWO) {
                Die d = new Die(ColorANSI.ANSI_YELLOW);
                d.setNumber(THREE);
                p2.getCell(i, j).placeDie(d);
            }
            if(j==3) {
                Die d = new Die(ColorANSI.ANSI_BLUE);
                d.setNumber(THREE);
                p2.getCell(i, j).placeDie(d);
            }
            if(j==FOUR){
                    Die d = new Die(ColorANSI.ANSI_PURPLE);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(ROW*SIX, cr.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = TWO; i < 3 ; i++){
            for(int j = 0; j<FIVE; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p3.getCell(i, j).setNumberConstraint(THREE);
                if(j == 0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==ONE) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==TWO) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==FOUR){
                    Die d = new Die(ColorANSI.ANSI_PURPLE);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(SIX, cr.calculateCardPoints(p3));

    }


    @Test
    void ColumnColorVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard cc = new ColumnColorVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(THREE);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, cc.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p2.getCell(i, j).setNumberConstraint(THREE);
                if(i == 0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==ONE) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==TWO) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(COLUMN*FIVE, cc.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 0; i < FOUR ; i++){
            for(int j = TWO; j<3; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p3.getCell(i, j).setNumberConstraint(THREE);
                if(i==0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==ONE) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==TWO) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(COLUMN, cc.calculateCardPoints(p3));

    }


    @Test
    void RowShadeVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard sr = new RowShadeVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(THREE);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, sr.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(j == 0){
                    p2.getCell(i, j).setNumberConstraint(THREE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==ONE) {
                    p2.getCell(i, j).setNumberConstraint(FOUR);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(FOUR);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==TWO) {
                    p2.getCell(i, j).setNumberConstraint(TWO);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(TWO);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    p2.getCell(i, j).setNumberConstraint(SIX);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(SIX);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==FOUR){
                    p2.getCell(i, j).setNumberConstraint(FIVE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(FIVE);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(ROW*FIVE, sr.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = TWO; i < 3 ; i++){
            for(int j = 0; j<FIVE; j++){
                p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(j == 0){
                    p3.getCell(i, j).setNumberConstraint(THREE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==ONE) {
                    p3.getCell(i, j).setNumberConstraint(FIVE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(FIVE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==TWO) {
                    p3.getCell(i, j).setNumberConstraint(ONE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(ONE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    p3.getCell(i, j).setNumberConstraint(FOUR);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(FOUR);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==FOUR){
                    p3.getCell(i, j).setNumberConstraint(SIX);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(SIX);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(FIVE, sr.calculateCardPoints(p3));

    }

    @Test
    void ColumnShadeVArietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard sc = new ColumnShadeVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(THREE);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(THREE);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, sc.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(i == 0){
                    p2.getCell(i, j).setNumberConstraint(THREE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==ONE) {
                    p2.getCell(i, j).setNumberConstraint(FOUR);
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(FOUR);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==TWO) {
                    p2.getCell(i, j).setNumberConstraint(SIX);
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(SIX);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    p2.getCell(i, j).setNumberConstraint(ONE);
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(ONE);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(COLUMN*FOUR, sc.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 0; i < FOUR ; i++){
            for(int j = TWO; j<3; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(i==0){
                    p3.getCell(i, j).setNumberConstraint(THREE);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(THREE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==ONE) {
                    p3.getCell(i, j).setNumberConstraint(FIVE);
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(FIVE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==TWO) {
                    p3.getCell(i, j).setNumberConstraint(ONE);
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(ONE);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    p3.getCell(i, j).setNumberConstraint(TWO);
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(TWO);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(FOUR, sc.calculateCardPoints(p3));
    }


    @Test
    void RoundTest(){
        Player p1 = new Player(P1, null);
        p1.setNickname(P1);
        Player p2 = new Player(P2, null);
        p2.setNickname(P2);
        Player p3 = new Player(P3, null);
        p3.setNickname(P3);

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Round round = new Round(ONE,players);

        assertEquals(P1,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P2,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P3,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P3,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P2,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P1,round.getActivePlayer().getNickname());
        assertEquals(false,round.nextTurn());
        assertEquals(false,round.nextTurn());
        assertEquals(players.size()*TWO,round.getTurn());


    }

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


    /*----TOOLCARD TEST----*/




    //Test only LightShade12, 34 and 56 are the same.
    @Test
    void LightShade(){


        Pattern p = (Pattern)gr.readObject("src/test/test_resources/json/TwoPair12.json", Pattern.class);

        PublicObjectiveCard card = new LightShade12();

        assertEquals(FOUR, card.calculateCardPoints(p));

        //free all the cells
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLUMN; j++){
                p.getCell(i,j).freeCell();
            }
        }

        assertEquals(0, card.calculateCardPoints(p));

    }


    //Test only DifferendShade, ColorVariety is the same.
    @Test
    void DiffShade(){


        Pattern p = (Pattern)gr.readObject("src/test/test_resources/json/TwoSets.json", Pattern.class);

        PublicObjectiveCard card = new DifferendShade();

        assertEquals(TEN, card.calculateCardPoints(p));

        p = (Pattern)gr.readObject("src/test/test_resources/json/NoSets.json", Pattern.class);

        assertEquals(0, card.calculateCardPoints(p));

        //free all the cells
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLUMN; j++){
                p.getCell(i,j).freeCell();
            }
        }

        assertEquals(0, card.calculateCardPoints(p));

    }


    /*----DATABASE TEST----*/


    @Test
    void DatabaseTest(){

        final String TEST_USERNAME = "Test";
        final String TEST_PASSWORD = "Test";
        final int TEST_ID = 0;
        final int TEST_BESTSCORE = 0;

        Database db = Database.getDb();
        db.logout(TEST_USERNAME);

        try {
            assertEquals(true, db.login(TEST_USERNAME,TEST_PASSWORD));
        } catch (WrongPasswordException e) {

        } catch (NoUserException e) {

        }catch (AlreadyLoggedException e) {

        }
        assertEquals(TEST_ID,db.getID(TEST_USERNAME,TEST_PASSWORD));

        try {
            assertEquals(false, db.login(TEST_USERNAME,""));
        } catch (NoUserException e) {

        } catch (WrongPasswordException e) {
            assertEquals("Password errata !",e.getMessage());
        }catch (AlreadyLoggedException e) {

        }

        try {
            assertEquals(false, db.login("",""));
        } catch (NoUserException e) {
            assertEquals("Utente inesistente !",e.getMessage());
        } catch (WrongPasswordException e) {

        }catch (AlreadyLoggedException e) {
            
        }
        assertEquals(TEST_BESTSCORE, db.getBestScore(TEST_USERNAME));
        db.setBestScore(TEST_USERNAME, TEST_BESTSCORE+ONE);
        assertEquals(TEST_BESTSCORE+ONE, db.getBestScore(TEST_USERNAME));
        db.setBestScore(TEST_USERNAME, TEST_BESTSCORE);


    }

}
