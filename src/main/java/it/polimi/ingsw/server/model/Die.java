package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utility.ColorANSI;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/*
 * Created by FCaimi on 30/04/18
 */

public class Die implements Serializable{

    private int number;
    private String face;
    private ColorANSI color;

    private static final int DEFAULT_NUMBER = 0;
    private static final String DEFAULT_FACE = "\u2370";

    private static final String[] faces = {
            "\u2680", //1
            "\u2681", //2
            "\u2682", //3
            "\u2683", //4
            "\u2684", //5
            "\u2685"  //6
    };

    public Die(ColorANSI c){
        color = c;
        number = DEFAULT_NUMBER;
        face=DEFAULT_FACE;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        switch (number){
            case 1:
                face="\u2680";
                break;
            case 2:
                face="\u2681";
                break;
            case 3:
                face="\u2682";
                break;
            case 4:
                face="\u2683";
                break;
            case 5:
                face="\u2684";
                break;
            case 6:
                face="\u2685";
                break;

        }
    }

    public ColorANSI getColor() {
        return color;
    }

    public void setColor(ColorANSI color) {
        this.color = color;
    }

    public String getFace() {
        return face;
    }

    /**
     * roll the die
     */

    public void roll(){
        int count = faces.length;
        Random rand = new Random();
        int index = rand.nextInt(count);
        this.face = faces[index];
        switch (face){
            case "\u2680":
                number=1;
                break;
            case "\u2681":
                number=2;
                break;
            case "\u2682":
                number=3;
                break;
            case "\u2683":
                number=4;
                break;
            case "\u2684":
                number=5;
                break;
            case "\u2685":
                number=6;
                break;
        }
    }

    @Override
    public String toString() {
        return this.color.escape()+"["+face+"]"+ColorANSI.RESET;
    }

    public String toString2(){
        return this.color.escape()+face+ColorANSI.RESET;
    }

    public void dump(){
        System.out.println(this.toString()+getNumber());
    }

}
