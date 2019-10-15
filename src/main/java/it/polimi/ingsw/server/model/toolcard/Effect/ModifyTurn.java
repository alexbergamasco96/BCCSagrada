package it.polimi.ingsw.server.model.toolcard.Effect;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.toolcard.Effect.Effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ModifyTurn extends Effect {

    //public Game game;
    public ArrayList<String> effects;
    public ArrayList<Integer> values;


    //Setup the effect

    @Override
    public void useToolcard(Game game, Map<String, ArrayList<String>> effects, ArrayList<Integer> values) throws IllegalActionException, WrongResources {

        //this.game = game;
        this.effects = effects.get("ModifyTurn");
        this.values = values;

        if(this.effects.contains("EXTRACT RESERVE")){
            if(game.getCurrentRound().getReserve().size() == 0){
                throw new WrongResources(WrongResourcesType.NO_RESERVE.toString());
            }
            if(game.getCurrentRound().getTurn() > game.getCurrentRound().getPlayers().size()){
                for(Die d : game.getCurrentRound().getReserve()){
                    d.roll();
                }
            }else{
                throw new IllegalActionException(IllegalActionExceptionType.WRONG_TURN.toString());
            }
        }



    }


}
