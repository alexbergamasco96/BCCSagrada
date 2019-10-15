package it.polimi.ingsw.server.controller;

public enum TimerHeader {


    GAME("gameTimer"),
    PATTERNCHOICE("patternChoiceTimer"),
    TURN("turnTimer");




    private final String text;

    TimerHeader(final String text){
        this.text=text;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
