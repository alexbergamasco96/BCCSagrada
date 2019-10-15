package it.polimi.ingsw.exception;

/*
 * Created by FCaimi on 13/05/18
 */

public class OutOfRoundTrackException extends Exception{
    public OutOfRoundTrackException(){
        super("Stai posizionando o cercando di prendere un dado oltre i 10 round del RoundTrack");
    }
}
