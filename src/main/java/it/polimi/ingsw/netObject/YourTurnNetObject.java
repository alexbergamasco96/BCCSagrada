package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;

public class YourTurnNetObject extends NetObject {

    String username;

    public YourTurnNetObject(String username) {
        super(MessageHeaderEnum.YOURTURN.toString());
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
