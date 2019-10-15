package it.polimi.ingsw.exception;

public class StopException extends Exception{

    public StopException(){
        super("End of turn");
    }

}
