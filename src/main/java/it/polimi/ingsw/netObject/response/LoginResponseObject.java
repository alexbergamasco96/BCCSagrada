package it.polimi.ingsw.netObject.response;

public class LoginResponseObject extends ResponseNetObject {

    private String username;

    public LoginResponseObject(String header, String response, String username) {
        super(header, response);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
