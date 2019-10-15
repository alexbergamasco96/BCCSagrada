package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;


public class RowShadeVariety extends PublicObjectiveCard {

    final static int VALUE = 5;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public RowShadeVariety(){
        super("Sfumature diverse - Riga","Righe senza sfumature ripetute");
    }



    public int calculateCardPoints(Pattern p){

        int points = 0;

        for(int i=0; i<ROW; i++){
            int j=0;
            if(p.getCell(i,j).isUsed()&&p.getCell(i,j+1).isUsed()&&p.getCell(i,j+2).isUsed()&&
                    p.getCell(i,j+3).isUsed()&&p.getCell(i,j+4).isUsed()){
                if((p.getCell(i,j).getDie().getNumber()!=p.getCell(i,j+1).getDie().getNumber())&&
                        (p.getCell(i,j).getDie().getNumber()!=p.getCell(i,j+2).getDie().getNumber())&&
                        (p.getCell(i,j).getDie().getNumber()!=p.getCell(i,j+3).getDie().getNumber())&&
                        (p.getCell(i,j).getDie().getNumber()!=p.getCell(i,j+4).getDie().getNumber())&&
                        (p.getCell(i,j+1).getDie().getNumber()!=p.getCell(i,j+2).getDie().getNumber())&&
                        (p.getCell(i,j+1).getDie().getNumber()!=p.getCell(i,j+3).getDie().getNumber())&&
                        (p.getCell(i,j+1).getDie().getNumber()!=p.getCell(i,j+4).getDie().getNumber())&&
                        (p.getCell(i,j+2).getDie().getNumber()!=p.getCell(i,j+3).getDie().getNumber())&&
                        (p.getCell(i,j+2).getDie().getNumber()!=p.getCell(i,j+4).getDie().getNumber())&&
                        (p.getCell(i,j+3).getDie().getNumber()!=p.getCell(i,j+4).getDie().getNumber())){
                    points+=1;
                }
            }
        }

        return VALUE * points;

    }



}
