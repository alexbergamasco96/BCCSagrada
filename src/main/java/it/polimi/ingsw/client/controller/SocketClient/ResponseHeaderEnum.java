package it.polimi.ingsw.client.controller.SocketClient;

public enum ResponseHeaderEnum {

    OK("ok"),
    NO("no"),
    EXCEPTION("exceptionLaunched");

    private final String text;

    ResponseHeaderEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
