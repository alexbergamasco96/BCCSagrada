package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;

import java.util.ArrayList;

public class ForcedGameStartedNetObject extends NetObject {

    private String username;

    private PrivateObjectiveCard privateObjectiveCard;

    private ArrayList<PublicObjectiveCard> publicObjectiveCards;

    public ForcedGameStartedNetObject(String username, PrivateObjectiveCard privateObjectiveCard, ArrayList<PublicObjectiveCard> publicObjectiveCards) {
        super(MessageHeaderEnum.FORCEDPATTERNCHOICE.toString());
        this.username = username;
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public String getUsername() {
        return username;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }
}
