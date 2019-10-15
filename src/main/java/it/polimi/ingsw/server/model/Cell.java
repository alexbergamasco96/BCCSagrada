package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utility.ColorANSI;

import java.io.Serializable;

/*
 * Created by FCaimi on 1/05/18
 */

public class Cell implements Serializable{

    private Die die;
    private ColorANSI colorConstraint;
    private int numberConstraint;
    private boolean used;

    private static final int FREE_NUM = 0;

    /**
     * Makes a cell
     * @param colorConstraint null for no constraint
     * @param numberConstraint 0 for no constraint
     */

    public Cell(ColorANSI colorConstraint, int numberConstraint) {
        die=null;
        colorConstraint=colorConstraint;
        numberConstraint=numberConstraint;
        used=false;
    }

    public int getNumberConstraint() {
        return numberConstraint;
    }

    public void setNumberConstraint(int numberConstraint) {
        this.numberConstraint = numberConstraint;
    }

    public ColorANSI getColorConstraint() {

        return colorConstraint;
    }

    public void setColorConstraint(ColorANSI colorConstraint) {
        this.colorConstraint = colorConstraint;
    }

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean b){
        used=b;
    }

    public void freeCell(){
        die =null;
        used=false;
    }

    public Die getDie(){
        return die;
    }

    public void setDie(Die die){
        this.die=die;
    }

    /**
     * places die into cell
     * @param d die
     * @return result
     */

    public boolean placeDie (Die d){


        if(isUsed()){

            return false;
        }
        if(colorConstraint!=null) {

            if (!colorConstraint.equals(d.getColor())) {

                return false;
            }
        }
        if(numberConstraint!=0){

            if(!(numberConstraint==d.getNumber())){

                return false;
            }
        }

        used=true;
        die=d;
        return true;
    }




    @Override
    public String toString() {
        if (isUsed()) {
            if(colorConstraint!=null) {
                return colorConstraint.escape() + "[" + die.toString2() + colorConstraint.escape() + "]" + ColorANSI.RESET;
            }
            return ColorANSI.RESET + "[" + die.toString2() + "]" + ColorANSI.RESET;
        }
        if(colorConstraint!=null) {
            return colorConstraint.escape() + "[" + numberConstraint + "]" + ColorANSI.RESET;
        }
        return ColorANSI.RESET + "[" + numberConstraint + "]" + ColorANSI.RESET;
    }

    public void dump(){
        System.out.print(this.toString());
    }


}
