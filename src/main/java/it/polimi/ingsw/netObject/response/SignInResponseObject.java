package it.polimi.ingsw.netObject.response;

import it.polimi.ingsw.client.controller.SocketClient.ResponseHeaderEnum;
import it.polimi.ingsw.server.controller.MessageHeaderEnum;

public class SignInResponseObject extends ResponseNetObject {

    private String username;

    public SignInResponseObject(String header,String username) {
        super(header, ResponseHeaderEnum.OK.toString());
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
