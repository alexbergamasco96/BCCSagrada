package it.polimi.ingsw.netObject;

import it.polimi.ingsw.server.model.Pattern;

public class PatternSelectedNetObject extends NetObject {

    private Pattern patternSelected;

    private String username;

    public PatternSelectedNetObject(String header, Pattern patternSelected, String username) {
        super(header);
        this.patternSelected = patternSelected;
        this.username = username;
    }

    public Pattern getPatternSelected() {
        return patternSelected;
    }

    public String getUsername() {
        return username;
    }
}
