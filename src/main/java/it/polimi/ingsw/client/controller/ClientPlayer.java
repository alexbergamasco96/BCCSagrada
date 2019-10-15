package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.server.model.Pattern;

import java.io.Serializable;

public class ClientPlayer implements Serializable {

    private String username;

    private Pattern pattern;

    private int token;

    private boolean connected;

    public ClientPlayer(String username, Pattern pattern, int token) {
        this.username = username;
        this.pattern = pattern;
        this.token = token;
        this.connected=true;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
