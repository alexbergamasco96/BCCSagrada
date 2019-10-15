package it.polimi.ingsw.exception;

public class GameStartedException extends Exception{

    private String username;

    public GameStartedException(String username){
        super("The game has already started !");
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
