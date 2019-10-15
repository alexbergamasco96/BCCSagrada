package it.polimi.ingsw.exception;



/*
 * Created by alexbergamasco on 12/05/2018
 */

/*
 * Enum of all IllegalActionException messages
 */
public enum IllegalActionExceptionType {

    NO_DICE("non hai selezionato una cella con dado !"),
    ADD_ON_6("Non puoi sommare un dado 6"),
    DECREASE_ON_1("Non puoi diminuire un dado 1"),
    NUMBER_CONSTRAIN("Restrizione di sfumatura non rispettata"),
    COLOR_CONSTRAIN("Restrizione di colore non rispettata"),
    GENERAL_CONSTRAIN("Mossa non valida!"),
    WRONG_TURN("Non puoi usare questa carta al primo turno"),
    INVALID_DIE("Dado non valido"),
    NO_DIE_COLOR("Colori non corrispondenti"),
    NOT_ENOUGH_TOKEN("Non hai abbastanza token per utilizzare questa carta");


    private final String text;


    IllegalActionExceptionType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
