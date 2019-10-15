package it.polimi.ingsw.exception;

public class AlreadyUsedUsernameException extends Exception {

    public AlreadyUsedUsernameException(){
        super("Il nome utente è già stato utilizzato !");
    }

}
