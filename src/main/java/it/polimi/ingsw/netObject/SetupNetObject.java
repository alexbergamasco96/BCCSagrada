package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.util.ArrayList;

public class SetupNetObject extends NetObject {

    private PrivateObjectiveCard privateObjectiveCard;
    private ArrayList<PublicObjectiveCard> publicObjectiveCards;
    private ArrayList<Integer> patternNumbers;

    public SetupNetObject(String header, PrivateObjectiveCard privateObjectiveCard, ArrayList<PublicObjectiveCard> publicObjectiveCards, ArrayList<Integer> patternNumbers) {
        super(header);
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.patternNumbers = patternNumbers;
    }


    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public ArrayList<Integer> getPatternNumbers() {
        return patternNumbers;
    }
}



