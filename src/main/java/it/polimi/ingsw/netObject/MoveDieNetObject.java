package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;
import it.polimi.ingsw.server.model.Die;

public class MoveDieNetObject extends NetObject{

    private String username;
    private int diePosition;
    private int x;
    private int y;

    public MoveDieNetObject(String username, int diePosition, int x, int y) {
        super(MessageHeaderEnum.MOVE.toString());
        this.username=username;
        this.diePosition = diePosition;
        this.x = x;
        this.y = y;
    }


    public String getUsername() {
        return username;
    }

    public int getDiePosition() {
        return diePosition;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
