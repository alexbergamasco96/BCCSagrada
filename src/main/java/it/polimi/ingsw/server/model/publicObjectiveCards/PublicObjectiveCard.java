package it.polimi.ingsw.server.model.publicObjectiveCards;


/*
 * Created by alexbergamasco96 on 06/05/18
 */

import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.model.Pattern;

import java.io.Serializable;

public abstract class PublicObjectiveCard extends Card  implements Serializable{


    public PublicObjectiveCard(String name, String description){
      super(name, description);
    };

    /**
     * calculates player points
     * @param p player pattern
     * @return player points
     */

    public abstract int calculateCardPoints(Pattern p);

}
