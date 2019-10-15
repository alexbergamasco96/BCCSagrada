package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.*;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;
import it.polimi.ingsw.utility.GsonWriter;
import org.junit.jupiter.api.Test;


public class PublicObjectiveCardTest {

    final static int FULL_BAG_DIE = 90;

    final static int ROW = 4;
    final static int COLUMN = 5;

    final static int CONSTANT3 =3;
    final static int CONSTANT5 = 5;
    final static int CONSTANT6 =6;
    final static int CONSTANT4 =4;
    final static int CONSTANT1=1;
    final static int CONSTANT2 =2;


    GsonWriter gw = new GsonWriter();
    GsonReader gr = new GsonReader();


    @Test
    void RowColorVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard cr = new RowColorVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(CONSTANT3);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT3);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, cr.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p2.getCell(i, j).setNumberConstraint(CONSTANT3);
                if(j == 0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==1) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==2) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==4){
                    Die d = new Die(ColorANSI.ANSI_PURPLE);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(ROW*6, cr.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 2; i < 3 ; i++){
            for(int j = 0; j<5; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p3.getCell(i, j).setNumberConstraint(CONSTANT3);
                if(j == 0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==1) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==2) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==4){
                    Die d = new Die(ColorANSI.ANSI_PURPLE);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(6, cr.calculateCardPoints(p3));

    }


    @Test
    void ColumnColorVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard cc = new ColumnColorVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(CONSTANT3);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT3);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, cc.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p2.getCell(i, j).setNumberConstraint(CONSTANT3);
                if(i == 0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==1) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==2) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(COLUMN*5, cc.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 0; i < 4 ; i++){
            for(int j = 2; j<3; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p3.getCell(i, j).setNumberConstraint(CONSTANT3);
                if(i==0){
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==1) {
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==2) {
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(5, cc.calculateCardPoints(p3));

    }


    @Test
    void RowShadeVarietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard sr = new RowShadeVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(CONSTANT3);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT3);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, sr.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(j == 0){
                    p2.getCell(i, j).setNumberConstraint(CONSTANT3);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==1) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT4);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT4);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==2) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT2);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT2);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT6);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT6);
                    p2.getCell(i, j).placeDie(d);
                }
                if(j==4){
                    p2.getCell(i, j).setNumberConstraint(CONSTANT1);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT1);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(ROW*5, sr.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 2; i < 3 ; i++){
            for(int j = 0; j<5; j++){
                p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(j == 0){
                    p3.getCell(i, j).setNumberConstraint(CONSTANT3);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==1) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT5);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT5);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==2) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT1);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT1);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==3) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT4);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT4);
                    p3.getCell(i, j).placeDie(d);
                }
                if(j==4){
                    p3.getCell(i, j).setNumberConstraint(CONSTANT6);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT6);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(5, sr.calculateCardPoints(p3));

    }

    @Test
    void ColumnShadeVArietyTest(){

        Pattern p1 = new Pattern();
        PublicObjectiveCard sc = new ColumnShadeVariety();


        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN; j++){
                p1.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                p1.getCell(i, j).setNumberConstraint(CONSTANT3);
                Die d = new Die(ColorANSI.ANSI_RED);
                d.setNumber(CONSTANT3);
                p1.getCell(i, j).placeDie(d);
            }
        }

        assertEquals(0, sc.calculateCardPoints(p1));



        Pattern p2 = new Pattern();

        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j<COLUMN; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(i == 0){
                    p2.getCell(i, j).setNumberConstraint(CONSTANT3);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==1) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT4);
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT4);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==2) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT6);
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT6);
                    p2.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    p2.getCell(i, j).setNumberConstraint(CONSTANT1);
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT1);
                    p2.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(COLUMN*4, sc.calculateCardPoints(p2));



        Pattern p3 = new Pattern();

        for(int i = 0; i < 4 ; i++){
            for(int j = 2; j<3; j++){
                //p2.getCell(i, j).setColorConstraint(ColorANSI.ANSI_RED);
                if(i==0){
                    p3.getCell(i, j).setNumberConstraint(CONSTANT3);
                    Die d = new Die(ColorANSI.ANSI_RED);
                    d.setNumber(CONSTANT3);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==1) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT5);
                    Die d = new Die(ColorANSI.ANSI_GREEN);
                    d.setNumber(CONSTANT5);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==2) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT1);
                    Die d = new Die(ColorANSI.ANSI_YELLOW);
                    d.setNumber(CONSTANT1);
                    p3.getCell(i, j).placeDie(d);
                }
                if(i==3) {
                    p3.getCell(i, j).setNumberConstraint(CONSTANT2);
                    Die d = new Die(ColorANSI.ANSI_BLUE);
                    d.setNumber(CONSTANT2);
                    p3.getCell(i, j).placeDie(d);
                }
            }
        }

        assertEquals(4, sc.calculateCardPoints(p3));
    }

    //Test only LightShade12, 34 and 56 are the same.
    @Test
    void LightShade(){


        Pattern p = (Pattern)gr.readObject("src/test/test_resources/json/TwoPair12.json", Pattern.class);

        PublicObjectiveCard card = new LightShade12();

        assertEquals(4, card.calculateCardPoints(p));

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

        assertEquals(10, card.calculateCardPoints(p));

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

}
