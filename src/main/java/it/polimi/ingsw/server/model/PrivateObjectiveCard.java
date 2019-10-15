package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utility.ColorANSI;

import java.io.Serializable;

/*
 * Created by cisla73 on 03/05/18
 */

public class PrivateObjectiveCard extends Card implements Serializable{


    protected final static int ROW = 4;
    protected final static int COLUMN = 5;
    private ColorANSI targetColor;

    // costruttore
    public PrivateObjectiveCard(String name, String description, ColorANSI targetColor) {
        super(name, description);
        this.targetColor = targetColor;
    }

    public ColorANSI getTargetColor() {
        return targetColor;
    }

    /**
     * calculates player points of private objective
     * @param p player pattern
     * @return player private points
     */

    public int calculateCardPoints(Pattern p){

        int points = 0;

        for(int i=0; i<ROW; i++){
            for(int j=0; j<COLUMN; j++){
                if(p.getCell(i,j).isUsed()) {
                    if(p.getCell(i,j).getDie().getColor().equals(targetColor)){
                        points+=p.getCell(i,j).getDie().getNumber();
                    }
                }
            }
        }

        return points;

    }


}
