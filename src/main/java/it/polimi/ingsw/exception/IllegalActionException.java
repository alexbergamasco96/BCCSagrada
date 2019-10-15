package it.polimi.ingsw.exception;

/*
 * Created by FCaimi on 07/05/18
 */



public class IllegalActionException extends Exception {

    private IllegalActionExceptionType illegalActionExceptionType;

    public IllegalActionException(String s){
        super(s);
    }

    /*public IllegalActionException(IllegalActionExceptionType iaet){
        super();
        this.illegalActionExceptionType = iaet;

    }*/

    public IllegalActionException(IllegalActionExceptionType iaet, String s){
        super(s);
        this.illegalActionExceptionType = iaet;
    }

}
