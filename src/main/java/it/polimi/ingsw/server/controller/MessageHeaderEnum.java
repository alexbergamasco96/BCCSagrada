package it.polimi.ingsw.server.controller;

public enum MessageHeaderEnum {

    SIGNIN("signIn"),
    LOGIN("login"),
    TOOLCARD("toolcard"),
    MOVE("move"),
    RESPONSE("response"),
    PATTERN("pattern"),
    EXCEPTION("exceptionLaunched"),
    CLIENTGAME("clientGame"),
    ENDTURN("endTurn"),
    ENDGAME("endGame"),
    YOURTURN("yourturn"),
    FORCEDPATTERNCHOICE("forcedPatternChoice"),
    DIETOOL11("dieTool11"),
    SETUP("setup");




    private final String text;


    MessageHeaderEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
