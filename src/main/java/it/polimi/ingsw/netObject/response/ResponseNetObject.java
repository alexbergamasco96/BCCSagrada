package it.polimi.ingsw.netObject.response;

import it.polimi.ingsw.netObject.NetObject;

public class ResponseNetObject extends NetObject {

    private String Response;

    public ResponseNetObject(String header, String response) {
        super(header);
        Response = response;
    }

    public String getResponse() {
        return Response;
    }
}
