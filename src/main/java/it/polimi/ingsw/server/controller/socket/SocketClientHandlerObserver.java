package it.polimi.ingsw.server.controller.socket;

import it.polimi.ingsw.netObject.NetObject;

import it.polimi.ingsw.server.controller.socket.SocketClientHandler;

public interface SocketClientHandlerObserver {

    public void onActionObjectReceived(NetObject actionObject, SocketClientHandler clientHandler);

    public void onDisconnection(String username);

}
