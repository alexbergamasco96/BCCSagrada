package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;

public class EndTurnNetObject extends NetObject {

    String username;


    public EndTurnNetObject(String username) {
        super(MessageHeaderEnum.ENDTURN.toString());
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
