package it.polimi.ingsw.server.model.toolcard.Effect;

import com.sun.prism.shader.AlphaOne_ImagePattern_AlphaTest_Loader;
import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.exception.IllegalActionExceptionType;
import it.polimi.ingsw.exception.WrongResources;
import it.polimi.ingsw.exception.WrongResourcesType;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.RoundTrack;
import it.polimi.ingsw.server.model.toolcard.Effect.Effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Move extends Effect {

    //public Game game;
    public ArrayList<String> effects;
    public ArrayList<Integer> values;


    //Setup the effect

    @Override
    public void useToolcard(Game game, Map<String, ArrayList<String>> effects, ArrayList<Integer> values) throws IllegalActionException, WrongResources {

        //this.game = game;
        this.effects = effects.get("Move");
        this.values = values;

        Pattern fromPattern = null;
        Pattern toPattern = null;
        RoundTrack roundTrack;
        Die d1 = null;
        Die d2 = null;
        ArrayList<Die> reserve = null;

        /*
         * Scan all the Move_Effects
         *
         * val(0) Y of first die
         * val(1) X of first die
         * val(2) Y first destination
         * val(3) X first destination
         * val(4) Y of second die
         * val(5) X of second die
         * val(6) Y second destination
         * val(7) X second destination
         *
         *
         */


        if(this.effects.contains("VARIABLE")){

            int num = values.get(0);
            System.out.println(values);
            values.remove(0);

            switch (num){
                case 1:
                    break;
                case 2:
                    this.effects.add("TWICE");
                    break;
                default: throw new WrongResources("Puoi scegliere al massimo 2 dadi");
            }

        }

        if(this.effects.contains("ONLY ON FIRST TURN")){

            if((game.getCurrentRound().getTurn() / (game.getCurrentRound().getPlayers().size() +1)) > 0){

                throw new IllegalActionException("Puoi usare questa carta solamente nel tuo primo turno");

            }

        }

        if(this.effects.contains("FROM RESERVE")){

            values.add(null);
            values.set(3, values.get(2));
            values.set(2, values.get(1));

            reserve = game.getCurrentRound().getReserve();
            if(reserve.size() == 0){
                throw new WrongResources(WrongResourcesType.NO_RESERVE.toString());
            }

            d1 = reserve.get(values.get(0)-1);


        }

        if(this.effects.contains("FROM PATTERN")){
            fromPattern = game.getCurrentRound().getActivePlayer().getPattern();
            d1 = getDieFromPattern(fromPattern, values.get(0)-1, values.get(1)-1);
            //try to fix saturday 07/07
            fromPattern.getCell(values.get(1)-1, values.get(0)-1).setUsed(false);

        }


        if(this.effects.contains("TO PATTERN")){
            toPattern = game.getCurrentRound().getActivePlayer().getPattern();

            if(this.effects.contains("CHECK COLOR IN ROUNDTRACK")){

                if(!this.effects.contains("TWICE")) {

                    int flag = 0;
                    for (ArrayList<Die> arrayList : game.getRoundTrack().getTrack()) {
                        for (Die d : arrayList) {
                            if (d.getColor().equals(d1.getColor())) {
                                flag = 1;
                            }
                        }
                    }
                    if (flag == 0) {
                        throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                    }



                    try {
                        placeDieToPattern(toPattern, d1, values.get(2) - 1, values.get(3) - 1);
                    } catch (Exception e) {
                        if (fromPattern != null) {
                            fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).setUsed(true);

                        }
                        throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                    }
                    //if function doesn't catch any exception:
                    if (fromPattern != null) {
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).freeCell();
                    }
                    if (reserve != null) {
                        reserve.remove(d1);
                    }
                }

            }else {

                try{
                    placeDieToPattern(toPattern, d1, values.get(2) - 1, values.get(3) - 1);
                }catch (Exception e){
                    if(fromPattern != null) {
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).setUsed(true);
                    }
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }

                if (!this.effects.contains("TWICE")) {
                    if (fromPattern != null) {
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).freeCell();
                    }
                    if (reserve != null) {
                        reserve.remove(d1);
                    }
                }

            }

        }


        if(this.effects.contains("TWICE")){

            d2 = getDieFromPattern(fromPattern, values.get(4)-1, values.get(5)-1);
            fromPattern.getCell(values.get(5)-1, values.get(4)-1).setUsed(false);
            if(this.effects.contains("CHECK COLOR IN ROUNDTRACK")) {

                if (!(d1.getColor().equals(d2.getColor()))) {
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                int flag = 0;
                for (ArrayList<Die> arrayList : game.getRoundTrack().getTrack()) {
                    for (Die d : arrayList) {
                        if (d.getColor().equals(d1.getColor())) {
                            flag = 1;
                        }
                    }
                }
                if (flag == 0) {
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                try {
                    placeDieToPattern(toPattern, d1, values.get(2) - 1, values.get(3) - 1);
                } catch (Exception e) {
                    if (fromPattern != null) {
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).setUsed(true);

                    }
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                //if function doesn't catch any exception:

            }
                /*
                try{
                    placeDieToPattern(toPattern, d2, values.get(6)-1, values.get(7)-1);
                }catch (Exception e){
                    toPattern.getCell(values.get(3) - 1, values.get(2) - 1).freeCell();
                    //fix line
                    if(fromPattern != null) {
                        fromPattern.getCell(values.get(5) - 1, values.get(4) - 1).setUsed(true);
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).setUsed(true);
                        fromPattern.getCell(values.get(3)-1, values.get(2)-1).freeCell();

                    }
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }

                if (fromPattern != null) {
                    fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).freeCell();
                    fromPattern.getCell(values.get(5)-1, values.get(4)-1).freeCell();

                }
                if (reserve != null) {
                    reserve.remove(d1);
                    reserve.remove(d2);
                }
                */
            //}else {

                //d2 = getDieFromPattern(fromPattern, values.get(4) - 1, values.get(5) - 1);
                try{
                    placeDieToPattern(toPattern, d2, values.get(6) - 1, values.get(7) - 1);
                }catch (Exception e){
                    toPattern.getCell(values.get(3) - 1, values.get(2) - 1).freeCell();
                    if(fromPattern != null) {
                        fromPattern.getCell(values.get(5) - 1, values.get(4) - 1).setUsed(true);
                        fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).setUsed(true);
                        fromPattern.getCell(values.get(3)-1, values.get(2)-1).freeCell();

                    }
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                //fromPattern.getCell(values.get(1)-1, values.get(0)-1).freeCell();

                fromPattern.getCell(values.get(5) - 1, values.get(4) - 1).freeCell();
                fromPattern.getCell(values.get(1) - 1, values.get(0) - 1).freeCell();
            //}
        }

        if(this.effects.contains("NO SECOND TURN")){

            game.getCurrentRound().getNoTurnTwo().add(game.getCurrentRound().getActivePlayer());
        }


    }

    private Die getDieFromPattern(Pattern fromPattern, int y, int x) throws WrongResources{

        Die d;

        if(fromPattern.getCell(x,y).getDie() == null){
            throw  new WrongResources(WrongResourcesType.NO_DICE.toString());
        }else{
            d = fromPattern.getCell(x,y).getDie();
        }

        return d;

    }

    private void placeDieToPattern(Pattern toPattern, Die d, int y, int x) throws WrongResources, IllegalActionException{
        System.out.println(x + " " + y);


        if(effects.contains("ALLOW COLOR")){
            if(toPattern.getCell(x,y).getNumberConstraint() != 0 && toPattern.getCell(x,y).getNumberConstraint() != d.getNumber()){
                throw new IllegalActionException(IllegalActionExceptionType.NUMBER_CONSTRAIN.toString());
            }else {
                toPattern.checkProximitySameColor(d,y,x);
                toPattern.checkProximitySameValue(d,y,x);
                if(!toPattern.checkProximity(x,y)){
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                toPattern.getCell(x,y).setDie(d);
                toPattern.getCell(x,y).setUsed(true);
            }
        }
        else if(effects.contains("ALLOW SHADE")){
            if(toPattern.getCell(x,y).getColorConstraint() != null && toPattern.getCell(x,y).getColorConstraint() != d.getColor()){
                throw new IllegalActionException(IllegalActionExceptionType.COLOR_CONSTRAIN.toString());
            }else {
                toPattern.checkProximitySameColor(d,y,x);
                toPattern.checkProximitySameValue(d,y,x);
                if(!toPattern.checkProximity(x,y)){
                    throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
                }
                toPattern.getCell(x,y).setDie(d);
                toPattern.getCell(x,y).setUsed(true);
            }
        }
        else if(effects.contains("NO PROXIMITY")){
            //control no proximity
            toPattern.placeDieWithNoProximity(d, y, x);

        }
        else{/*
            boolean res = toPattern.getCell(x,y).placeDie(d);
            if(!res){
                throw new IllegalActionException(IllegalActionExceptionType.GENERAL_CONSTRAIN.toString());
            }else {

                toPattern.getCell(x,y).setDie(d);
                toPattern.getCell(x,y).setUsed(true);
                //toPattern.getCell(x,y).freeCell();
            }
            */
            toPattern.placeDie(d, y, x);

        }


    }

}
