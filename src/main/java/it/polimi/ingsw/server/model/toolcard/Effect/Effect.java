package it.polimi.ingsw.server.model.toolcard.Effect;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.exception.WrongResources;
import it.polimi.ingsw.server.model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public abstract class Effect implements Serializable {


    /**
     * actives toolCard power
     * @param game current game
     * @param effects array of toolCard powers
     * @param values parameters needed from the toolcard
     * @throws IllegalActionException
     * @throws WrongResources
     */

    public abstract void useToolcard(Game game, Map<String, ArrayList<String>> effects, ArrayList<Integer> values) throws IllegalActionException, WrongResources;

}
