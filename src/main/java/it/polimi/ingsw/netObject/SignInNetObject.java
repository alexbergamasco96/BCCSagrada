package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.controller.MessageHeaderEnum;

public class SignInNetObject extends NetObject {

    private String username;
    private String password;

    public SignInNetObject(String username, String password) {
        super(MessageHeaderEnum.SIGNIN.toString());
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
