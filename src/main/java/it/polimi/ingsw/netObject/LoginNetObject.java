package it.polimi.ingsw.netObject;

public class LoginNetObject extends NetObject {

    private String username;
    private String password;

    public LoginNetObject(String header, String username, String password) {
        super(header);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
