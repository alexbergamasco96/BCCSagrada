package it.polimi.ingsw.client.view.CLI;

public enum AuthenticationType {

    LOGIN("login"),
    SIGNIN("signin");

    private String name;

    AuthenticationType(String name){

        this.name = name;

    }

    @Override
    public String toString(){

        return this.name;

    }


}
