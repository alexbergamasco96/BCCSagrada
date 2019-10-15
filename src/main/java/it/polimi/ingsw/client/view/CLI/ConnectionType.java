package it.polimi.ingsw.client.view.CLI;

public enum ConnectionType {

    SOCKET("1"),
    RMI("2");

    private String name;

    ConnectionType(String name){

        this.name = name;

    }

    @Override
    public String toString(){

        return this.name;

    }

}
