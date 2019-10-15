package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;


public class RowColorVariety extends PublicObjectiveCard {

    final static int VALUE = 6;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public RowColorVariety(){
        super("Colori diversi - Riga", "Righe senza colori ripetuti");
    }

    public int calculateCardPoints(Pattern p){

        int points = 0;

        for(int i=0; i<ROW; i++){
            int j=0;
            if(p.getCell(i,j).isUsed()&&p.getCell(i,j+1).isUsed()&&p.getCell(i,j+2).isUsed()&&
                    p.getCell(i,j+3).isUsed()&&p.getCell(i,j+4).isUsed()){
                if((p.getCell(i,j).getDie().getColor()!=p.getCell(i,j+1).getDie().getColor())&&
                        (p.getCell(i,j).getDie().getColor()!=p.getCell(i,j+2).getDie().getColor())&&
                        (p.getCell(i,j).getDie().getColor()!=p.getCell(i,j+3).getDie().getColor())&&
                        (p.getCell(i,j).getDie().getColor()!=p.getCell(i,j+4).getDie().getColor())&&
                        (p.getCell(i,j+1).getDie().getColor()!=p.getCell(i,j+2).getDie().getColor())&&
                        (p.getCell(i,j+1).getDie().getColor()!=p.getCell(i,j+3).getDie().getColor())&&
                        (p.getCell(i,j+1).getDie().getColor()!=p.getCell(i,j+4).getDie().getColor())&&
                        (p.getCell(i,j+2).getDie().getColor()!=p.getCell(i,j+3).getDie().getColor())&&
                        (p.getCell(i,j+2).getDie().getColor()!=p.getCell(i,j+4).getDie().getColor())&&
                        (p.getCell(i,j+3).getDie().getColor()!=p.getCell(i,j+4).getDie().getColor())){
                        points+=1;
                }
            }
        }

        return VALUE * points;

    }

}
