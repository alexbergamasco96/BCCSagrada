package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;


import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.model.*;

import it.polimi.ingsw.server.model.toolcard.ToolCard;
import it.polimi.ingsw.utility.ColorANSI;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class ToolTest {

    private final int COLUMN = 5;
    private final int ROW = 4;

    private final int ADD = 1;
    private final int MIN = 2;
    private final int ERROR = 3;

    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;



    private final String P1 = "Alex";
    private final String P2 = "Simo";
    private final String P3 = "Fede";
    
    
    
    

    //ToolCard ONE, Pinza Sgrossatrice

    @Test
    void PinzaSgrossatrice(){

        ToolCard t = new ToolCard();
        t.newToolCard(ONE);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(ONE, players);
        round.setTurn(ONE);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Integer> input = new ArrayList<>();
        int i = ADD;
        int j = ONE;
        input.add(i);
        input.add(j);

        /*try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }*/

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(7);

        //ArrayList<Integer> input = new ArrayList<>();
        input.clear();
        i = ERROR;
        j = ONE;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        i = MIN;
        j = ONE;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.DECREASE_ON_1.toString(), e.getMessage());
        }
        input.clear();
        i = ADD;
        j = TWO;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.ADD_ON_6.toString(), e.getMessage());
        }

        input.clear();
        i = ADD;
        j = THREE;
        input.add(i);
        input.add(j);

        int last = game.getCurrentRound().getReserve().get(j-ONE).getNumber();
        try {
            t.useToolCard(game, input);
            assertEquals((last +ONE), reserve.get(j-ONE).getNumber());
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        i = MIN;
        j = THREE;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
            assertEquals((last), reserve.get(j-ONE).getNumber());
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setNickname(P3);

        input.clear();
        i = MIN;
        j = THREE;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.WRONG_TURN.toString(), e.getMessage());
        }


        input.clear();

        /*try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }*/

        game.getCurrentRound().getActivePlayer().setToken(ONE);

        input.clear();
        i = MIN;
        j = THREE;
        input.add(i);
        input.add(j);

        try {
            t.useToolCard(game, input);
            assertEquals((last), reserve.get(j-ONE).getNumber());
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard TWO, Pannello per Eglomise

    @Test
    void PannelloPerEglomise(){


        ToolCard t = new ToolCard();
        t.newToolCard(TWO);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(ONE, players);
        Die d = new Die(ColorANSI.ANSI_BLUE);
        d.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_RED);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_PURPLE);
        d3.setNumber(FOUR);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,ONE).setDie(d3);
        p.getCell(THREE,ONE).setUsed(true);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setPattern(p);
        game.getCurrentRound().getActivePlayer().setToken(10);

        try{
            p.placeDie(d,ONE,0);
        }catch (IllegalActionException e){
            e.printStackTrace();
        }

        p.dump();

        ArrayList<Integer> input = new ArrayList<>();
        input.add(TWO);
        input.add(ONE);
        input.add(FIVE);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(THREE);

        p.dump();

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        p.dump();

        input.clear();
        input.add(FIVE);
        input.add(FOUR);
        input.add(FOUR);
        input.add(TWO);

        p.dump();

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        p.dump();

        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(FIVE);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        p.dump();

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.CELL_ALREADY_USED.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        input.clear();
        input.add(THREE);
        input.add(TWO);
        input.add(FOUR);
        input.add(TWO);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        input.add(FOUR);
        input.add(TWO);
        input.add(TWO);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(FOUR);
        input.add(TWO);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard THREE, Alesatore per lamina di rame

    @Test
    void AlesatorePerLaminaDiRame(){


        ToolCard t = new ToolCard();
        t.newToolCard(THREE);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(ONE, players);
        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setPattern(p);
        game.getCurrentRound().getActivePlayer().setToken(10);

        try{
            p.placeDie(d,ONE,0);
        }catch (IllegalActionException e){
            e.printStackTrace();
        }

        ArrayList<Integer> input = new ArrayList<>();
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(ONE);
        input.add(ONE);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }


        input.clear();
        input.add(ONE);
        input.add(ONE);
        input.add(FIVE);
        input.add(FOUR);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(THREE);
        input.add(FOUR);



        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.CELL_ALREADY_USED.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(FOUR);
        input.add(TWO);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard FOUR, Lathekin

    @Test
    void Lathekin(){


        ToolCard t = new ToolCard();
        t.newToolCard(FOUR);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(ONE, players);
        Die d = new Die(ColorANSI.ANSI_RED);
        d.setNumber(FIVE);
        Die d2 = new Die(ColorANSI.ANSI_BLUE);
        d2.setNumber(TWO);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);



        ArrayList<Integer> input = new ArrayList<>();

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(TWO,ONE).setDie(d2);
        p.getCell(TWO,ONE).setUsed(true);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setPattern(p);
        game.getCurrentRound().getActivePlayer().setToken(10);

        try{
            p.placeDie(d,ONE,0);
        }catch (IllegalActionException e){
            e.printStackTrace();
        }

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        input.add(THREE);
        input.add(FOUR);
        input.add(TWO);
        input.add(ONE);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(ONE);
        input.add(ONE);

        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(ONE);
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        p.dump();

        input.clear();
        input.add(TWO);
        input.add(THREE);
        input.add(TWO);
        input.add(TWO);

        input.add(THREE);
        input.add(FOUR);
        input.add(THREE);
        input.add(ONE);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        input.add(THREE);
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);

        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        p.dump();

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(THREE);

        input.add(TWO);
        input.add(FOUR);
        input.add(FOUR);
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(TWO);
        input.add(FOUR);
        input.add(ONE);
        input.add(ONE);

        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard FIVE, Taglierina circolare

    @Test
    void TaglierinaCircolare(){

        ToolCard t = new ToolCard();
        t.newToolCard(FIVE);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Integer> input = new ArrayList<>();
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);

        System.out.println(reserve);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(10);

        game.getRoundTrack().getTrack().set(0, reserve);

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        try {
            game.getRoundTrack().insertDie(ONE,d4);
            game.getRoundTrack().insertDie(ONE,d5);
        } catch (OutOfRoundTrackException e) {
            e.printStackTrace();
        }

        System.out.println(game.getRoundTrack().getDice(ONE));

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println(reserve);
        System.out.println(game.getRoundTrack().getDice(ONE));

        input.clear();
        input.add(FOUR);
        input.add(ONE);
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }


    }

    //ToolCard SIX, Pennello per Pasta Salda

    @Test
    void PennelloPerPastaSalda(){

        ToolCard t = new ToolCard();
        t.newToolCard(SIX);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(ONE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        System.out.println(reserve);

        game.setCurrentRound(round);

        round.setReserve(reserve);

        game.getCurrentRound().getActivePlayer().setToken(10);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);

        players.get(0).setPattern(p);

        p.dump();

        ArrayList<Integer> input = new ArrayList<>();

        input.add(TWO);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println(reserve);

        input.clear();
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        input.clear();
        input.add(7);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        input.clear();
        input.add(TWO);
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(TWO);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard 7, Martelletto

    @Test
    void Martelletto(){

        ToolCard t = new ToolCard();
        t.newToolCard(7);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(10);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);


        ArrayList<Integer> input = new ArrayList<>();

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.WRONG_TURN.toString(), e.getMessage());
        }

        round.setTurn(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        game.getCurrentRound().getActivePlayer().setToken(0);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }


    }


    //ToolCard 8, Tenaglia a Rotelle

    @Test
    void TenagliaARotelle(){

        ToolCard t = new ToolCard();
        t.newToolCard(8);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(TWO, players);
        round.setTurn(ONE);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(13);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);
        p.getCell(TWO,THREE).setDie(d5);
        p.getCell(TWO,THREE).setUsed(true);

        players.get(0).setPattern(p);

        ArrayList<Integer> input = new ArrayList<>();
        input.add(ONE);
        input.add(FOUR);
        input.add(FOUR);

        players.get(0).getPattern().dump();
        System.out.println(reserve);
        System.out.println(input);

        p.setFirsPlace(true);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
       }


        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        input.clear();
        input.add(THREE);
        input.add(FIVE);
        input.add(FIVE);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }


        input.clear();
        input.add(ONE);
        input.add(FOUR);
        input.add(FOUR);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        round.getReserve().clear();


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_RESERVE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard 9, Riga in Sughero

    @Test
    void RigaInSughero(){

        ToolCard t = new ToolCard();
        t.newToolCard(9);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Integer> input = new ArrayList<>();

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(20);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);

        players.get(0).setPattern(p);

        System.out.println(reserve);
        p.dump();

        input.clear();

        input.add(TWO);
        input.add(THREE);
        input.add(THREE);

        System.out.println(input);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }
        System.out.println("test 1 ok");

        input.clear();
        input.add(TWO);
        input.add(ONE);
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        System.out.println("test 2 ok");

        input.clear();
        input.add(THREE);
        input.add(FIVE);
        input.add(FIVE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        System.out.println("test 3 ok");


        input.clear();
        input.add(TWO);
        input.add(SIX);
        input.add(THREE);

        System.out.println(reserve);
        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString(), e.getMessage());
        }

        System.out.println("test 4 ok");

        input.clear();
        input.add(ONE);
        input.add(FOUR);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.CELL_ALREADY_USED.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println("test 5 ok");


        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(ONE);
        input.add(TWO);
        input.add(FOUR);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

        System.out.println("test 6 ok");

    }


    //ToolCard 10, Tampone Diamantato

    @Test
    void TamponeDiamantato(){


        ToolCard t = new ToolCard();
        t.newToolCard(10);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_YELLOW);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(SIX);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(10);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(THREE,TWO).setDie(d3);
        p.getCell(THREE,TWO).setUsed(true);

        System.out.println(reserve);

        ArrayList<Integer> input = new ArrayList<>();
        input.add(8);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println(reserve);

        round.getReserve().clear();

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.ERROR_ON_INPUT.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        game.getCurrentRound().getActivePlayer().setToken(0);

        try {
            t.useToolCard(game, input);
            assertEquals(THREE, reserve.get(reserve.size()-ONE).getNumber());
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

    }


    //ToolCard 11, Diluente per PastaSalda

    @Test
    void DiluentePerPastaSalda(){

        ToolCard t = new ToolCard();
        t.newToolCard(11);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(P1, null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_BLUE);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(ONE);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(10);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(TWO,TWO).setDie(d3);
        p.getCell(TWO,TWO).setUsed(true);

        players.get(0).setPattern(p);

        round.setTurn(ONE);

        game.getRoundTrack().getTrack().set(0, reserve);

        System.out.println(game.getRoundTrack().getTrack().get(0));
        players.get(0).getPattern().dump();


        ArrayList<Integer> input = new ArrayList<>();
        input.add(ONE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println(reserve);

        input.clear();
        input.add(ONE);
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

        System.out.println(reserve);

        game.getCurrentRound().getActivePlayer().setToken(ONE);

        input.clear();
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }

        System.out.println(reserve);

        game.getCurrentRound().getActivePlayer().setToken(TWO);

        round.getReserve().clear();
        input.clear();
        input.add(THREE);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_RESERVE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }

    }


    //ToolCard 12, Taglierina Manuale

    @Test
    void TaglierinaManuale(){

        ToolCard t = new ToolCard();
        t.newToolCard(12);
        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("P1", null));
        players.add(new Player(P2, null));
        players.add(new Player(P3, null));
        Round round = new Round(THREE, players);

        Die d1 = new Die(ColorANSI.ANSI_RED);
        d1.setNumber(ONE);
        Die d2 = new Die(ColorANSI.ANSI_GREEN);
        d2.setNumber(SIX);
        Die d3 = new Die(ColorANSI.ANSI_BLUE);
        d3.setNumber(FOUR);
        Die d4 = new Die(ColorANSI.ANSI_RED);
        d4.setNumber(THREE);
        Die d5 = new Die(ColorANSI.ANSI_BLUE);
        d5.setNumber(ONE);

        ArrayList<Die> reserve = new ArrayList<>();
        reserve.add(d1);
        reserve.add(d2);
        reserve.add(d3);
        reserve.add(d4);
        reserve.add(d5);

        round.setReserve(reserve);
        game.setCurrentRound(round);
        game.getCurrentRound().getActivePlayer().setToken(10);

        Pattern p = new Pattern();
        p.getCell(0,0).setNumberConstraint(ONE);
        p.getCell(THREE,ONE).setNumberConstraint(FIVE);
        p.getCell(THREE,FOUR).setColorConstraint(ColorANSI.ANSI_GREEN);
        p.getCell(TWO,TWO).setDie(d3);
        p.getCell(TWO,TWO).setUsed(true);
        p.getCell(THREE,TWO).setDie(d2);
        p.getCell(THREE,TWO).setUsed(true);
        p.getCell(THREE,THREE).setDie(d5);
        p.getCell(THREE,THREE).setUsed(true);

        players.get(0).setPattern(p);

        p.dump();

        p.setFirsPlace(true);
        round.setTurn(ONE);

        game.getRoundTrack().getTrack().set(0, reserve);

        System.out.println(game.getRoundTrack().getTrack().get(0));
        players.get(0).getPattern().dump();


        ArrayList<Integer> input = new ArrayList<>();
        input.add(TWO);
        input.add(THREE);
        input.add(THREE);
        input.add(TWO);
        input.add(THREE);
        input.add(FOUR);
        input.add(FOUR);
        input.add(FOUR);
        input.add(THREE);


        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        input.clear();
        input.add(ONE);
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(TWO);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        input.clear();
        input.add(ONE);
        input.add(FIVE);
        input.add(ONE);
        input.add(TWO);
        input.add(TWO);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals(WrongResourcesType.NO_DICE.toString(), wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals("", e.getMessage());
        }


        game.getCurrentRound().getActivePlayer().setToken(0);

        input.clear();
        input.add(ONE);
        input.add(TWO);
        input.add(ONE);
        input.add(TWO);
        input.add(TWO);

        p.dump();
        System.out.println(input);

        try {
            t.useToolCard(game, input);
        } catch (WrongResources wrongResources) {
            assertEquals("", wrongResources.getMessage());
        } catch (IllegalActionException e) {
            assertEquals(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString(), e.getMessage());
        }


    }


}
