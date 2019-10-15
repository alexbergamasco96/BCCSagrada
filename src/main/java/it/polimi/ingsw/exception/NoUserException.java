package it.polimi.ingsw.exception;

public class NoUserException extends Exception{

    public NoUserException(){
        super("Utente inesistente !");
    }

}
