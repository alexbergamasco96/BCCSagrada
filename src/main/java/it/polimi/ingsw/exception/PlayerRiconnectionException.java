package it.polimi.ingsw.exception;

public class PlayerRiconnectionException extends Exception {

    private String username;

    public PlayerRiconnectionException(String username) {
        super("Player "+username+" riconnected !");
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
