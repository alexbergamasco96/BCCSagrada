package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.model.Die;

public class DieTool11NetObject extends NetObject {

    private Die d;

    public DieTool11NetObject(Die d) {
        super(MessageHeaderEnum.DIETOOL11.toString());
        this.d = d;
    }

    public Die getD() {
        return d;
    }
}
