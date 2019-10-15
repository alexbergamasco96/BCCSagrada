package it.polimi.ingsw.server.model;


/*
 * Created by alexbergamasco96 on 02/05/18
 */

import java.io.Serializable;

public class PatternCard implements Serializable{

    private Pattern faceA;
    private Pattern faceB;


    public PatternCard(Pattern a, Pattern b){
        this.faceA = a;
        this.faceB = b;
    }

    public Pattern getPattern(boolean fronte) {

        if(fronte){
            return this.faceA;
        }
        else{
            return this.faceB;
        }

    }

    @Override
    public String toString(){
        String patterncard = new String();

        patterncard += this.faceA.toString();
        patterncard += "\n";
        patterncard += this.faceB.toString();
        return patterncard;
    }

    public void dump(){ System.out.println(this.toString()); }

}

