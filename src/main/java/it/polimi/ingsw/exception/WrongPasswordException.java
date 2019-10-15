package it.polimi.ingsw.exception;

public class WrongPasswordException extends Exception{

    public WrongPasswordException(){
        super("Password errata !");
    }

}
