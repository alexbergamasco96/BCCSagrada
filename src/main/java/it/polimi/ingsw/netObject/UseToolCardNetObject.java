package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;

import java.util.ArrayList;

public class UseToolCardNetObject extends NetObject {

    private String username;
    private int toolCardNumber;
    private ArrayList<Integer> params;

    public UseToolCardNetObject(String username, int toolCardNumber, ArrayList<Integer> params) {
        super(MessageHeaderEnum.TOOLCARD.toString());
        this.username = username;
        this.toolCardNumber = toolCardNumber;
        this.params = params;
    }

    public String getUsername() {
        return username;
    }

    public int getToolCardNumber() {
        return toolCardNumber;
    }

    public ArrayList<Integer> getParams() {
        return params;
    }
}
