package it.polimi.ingsw.netObject;

import it.polimi.ingsw.client.controller.ClientGame;

public class ClientGameNetObject extends NetObject {

    private ClientGame clientGame;

    public ClientGameNetObject(String header, ClientGame clientGame) {
        super(header);
        this.clientGame = clientGame;
    }

    public ClientGame getClientGame() {
        return clientGame;
    }
}
