package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;


public class ColumnColorVariety extends PublicObjectiveCard {

    final static int VALUE = 5;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public ColumnColorVariety(){
        super("Colori diversi - Colonna", "Colonne senza colori ripetuti");
    }

    public int calculateCardPoints(Pattern p){

        int points = 0;

        for(int j=0; j<COLUMN; j++){
            int i=0;
            if(p.getCell(i,j).isUsed()&&p.getCell(i+1,j).isUsed()&&p.getCell(i+2,j).isUsed()&&
                    p.getCell(i+3,j).isUsed()){
                if((p.getCell(i,j).getDie().getColor()!=p.getCell(i+1,j).getDie().getColor())&&
                        (p.getCell(i,j).getDie().getColor()!=p.getCell(i+2,j).getDie().getColor())&&
                        (p.getCell(i,j).getDie().getColor()!=p.getCell(i+3,j).getDie().getColor())&&
                        (p.getCell(i+1,j).getDie().getColor()!=p.getCell(i+2,j).getDie().getColor())&&
                        (p.getCell(i+1,j).getDie().getColor()!=p.getCell(i+3,j).getDie().getColor())&&
                        (p.getCell(i+2,j).getDie().getColor()!=p.getCell(i+3,j).getDie().getColor())){
                    points+=1;
                }
            }
        }

        return points * VALUE;

    }

}
