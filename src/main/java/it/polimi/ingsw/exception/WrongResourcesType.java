package it.polimi.ingsw.exception;


/*
    Created By alexbergamasco96 on 12/05/2018
 */


/*
  Enum of all WrongResources Exceptions
*/

public enum WrongResourcesType {

    NO_PATTERN("Manca pattern"),
    NO_DICE("Manca dado"),
    CELL_ALREADY_USED("Cella già occupata"),
    NO_RESERVE("Riserva nulla"),
    NO_ROUNDTRACK("Tracciato dei round vuoto, turno corrente: 1"),
    NO_BAG("Manca sacchetto"),
    ERROR_ON_INPUT("Input non corretto");




    private final String text;


    WrongResourcesType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
