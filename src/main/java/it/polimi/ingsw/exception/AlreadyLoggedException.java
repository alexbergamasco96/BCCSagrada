package it.polimi.ingsw.exception;

public class AlreadyLoggedException extends Exception{
    public AlreadyLoggedException(){
        super("Utente già loggato !");
    }
}
