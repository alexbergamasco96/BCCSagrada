package it.polimi.ingsw.server.model.toolcard.Effect;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ChangeValue extends Effect {

    //public Game game;
    public ArrayList<String> effects;
    public ArrayList<Integer> values;

    //Setup the effect

    @Override
    public void useToolcard(Game game, Map<String, ArrayList<String>> effects, ArrayList<Integer> values) throws IllegalActionException, WrongResources{

        //this.game = game;
        this.effects = effects.get("ChangeValue");
        this.values = values;


        /*
         * Scan all the ChangeValue_Effects
         *
         * UP/DOWN
         * values(0) defines UP or DOWN
         * values(1) defines the index of reserve
         *
         * OPPOSITE
         * values(1) defines the index of reserve
         *
         * ROLL
         *values(1) defines the index of reserve
         *
        */

        if(this.effects.contains("UP/DOWN")){

            //1: UP, 2: DOWN. Control of this toolcard on Client
            if(game.getCurrentRound().getReserve().size() == 0){
                throw new WrongResources(WrongResourcesType.NO_RESERVE.toString());
            }
            if(values.get(0) == 1){
                if( values.get(0) >( game.getCurrentRound().getReserve().size()-1) || values.get(0) < 0){
                    throw new WrongResources(WrongResourcesType.ERROR_ON_INPUT.toString());
                }
                if(game.getCurrentRound().getReserve().get(values.get(1)-1).getNumber() == 6){
                    throw new IllegalActionException(IllegalActionExceptionType.ADD_ON_6.toString());
                }else {
                    game.getCurrentRound().getReserve().get(values.get(1) - 1).setNumber(game.getCurrentRound().getReserve().get(values.get(1) - 1).getNumber() + 1);
                }

            }
            else if(values.get(0) == 2){
                if( values.get(0) >( game.getCurrentRound().getReserve().size()-1) || values.get(0) < 0){
                    throw new WrongResources(WrongResourcesType.ERROR_ON_INPUT.toString());
                }
                if(game.getCurrentRound().getReserve().get(values.get(1)-1).getNumber() == 1){
                    throw new IllegalActionException(IllegalActionExceptionType.DECREASE_ON_1.toString());
                }else {
                    game.getCurrentRound().getReserve().get(values.get(1) - 1).setNumber(game.getCurrentRound().getReserve().get(values.get(1) - 1).getNumber() - 1);
                }
            }

            else{
                throw new WrongResources(WrongResourcesType.ERROR_ON_INPUT.toString());
            }


        }
        if(this.effects.contains("OPPOSITE")){

            if( values.get(0) >( game.getCurrentRound().getReserve().size()) || values.get(0) < 0){
                throw new WrongResources(WrongResourcesType.ERROR_ON_INPUT.toString());
            }
            switch(game.getCurrentRound().getReserve().get(values.get(0)-1).getNumber()){

                    case 1:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(6);
                        break;
                    case 2:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(5);
                        break;
                    case 3:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(4);
                        break;
                    case 4:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(3);
                        break;
                    case 5:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(2);
                        break;
                    case 6:
                        game.getCurrentRound().getReserve().get(values.get(0)-1).setNumber(1);
                        break;


            }

        }
        if(this.effects.contains("ROLL")){

            if( values.get(0) >( game.getCurrentRound().getReserve().size()) || values.get(0) < 0){
                throw new WrongResources(WrongResourcesType.ERROR_ON_INPUT.toString());
            }

            game.getCurrentRound().getReserve().get(values.get(0)-1).roll();

            boolean old = true;
            if(!game.getCurrentRound().getActivePlayer().getPattern().isFirsPlace()){
                old = false;
            }
            if(game.getCurrentRound().getActivePlayer().getPattern().checkForcedPlacement(game.getCurrentRound().getReserve().get(values.get(0)-1))){


                game.getCurrentRound().getActivePlayer().setForcedDie(game.getCurrentRound().getReserve().get(values.get(0)-1));

                if(!old){
                    game.getCurrentRound().getActivePlayer().getPattern().setFirsPlace(false);
                }



            }


        }
    }
}
