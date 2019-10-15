package it.polimi.ingsw.netObject.response;

import it.polimi.ingsw.client.controller.SocketClient.ResponseHeaderEnum;
import it.polimi.ingsw.server.controller.MessageHeaderEnum;

public class DieTool11ResponseObject extends ResponseNetObject {

    private int dieNumber;

    public DieTool11ResponseObject(int dieNumber) {
        super(MessageHeaderEnum.DIETOOL11.toString(), ResponseHeaderEnum.OK.toString());
        this.dieNumber = dieNumber;
    }

    public int getDieNumber() {
        return dieNumber;
    }
}
