package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;


public class ColumnShadeVariety extends PublicObjectiveCard {

    final static int VALUE = 4;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public ColumnShadeVariety(){
        super("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute");
    }


    public int calculateCardPoints(Pattern p){

        int points = 0;

        for(int j=0; j<COLUMN; j++){
            int i=0;
            if(p.getCell(i,j).isUsed()&&p.getCell(i+1,j).isUsed()&&p.getCell(i+2,j).isUsed()&&
                    p.getCell(i+3,j).isUsed()){
                if((p.getCell(i,j).getDie().getNumber()!=p.getCell(i+1,j).getDie().getNumber())&&
                        (p.getCell(i,j).getDie().getNumber()!=p.getCell(i+2,j).getDie().getNumber())&&
                        (p.getCell(i,j).getDie().getNumber()!=p.getCell(i+3,j).getDie().getNumber())&&
                        (p.getCell(i+1,j).getDie().getNumber()!=p.getCell(i+2,j).getDie().getNumber())&&
                        (p.getCell(i+1,j).getDie().getNumber()!=p.getCell(i+3,j).getDie().getNumber())&&
                        (p.getCell(i+2,j).getDie().getNumber()!=p.getCell(i+3,j).getDie().getNumber())){
                    points+=1;
                }
            }
        }

        return points * VALUE;

    }

}
