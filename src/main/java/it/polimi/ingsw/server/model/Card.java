package it.polimi.ingsw.server.model;

/*
 * Created by cisla73 on 02/05/18
 */

import java.io.Serializable;

public abstract class Card implements Serializable{

    private String name;
    private String description;

    public Card(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Card(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

}
