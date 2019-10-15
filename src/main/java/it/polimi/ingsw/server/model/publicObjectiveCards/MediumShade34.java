package it.polimi.ingsw.server.model.publicObjectiveCards;


import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;

/*
 * Created by alexbergamasco96 on 06/05/18
 */

public class MediumShade34 extends PublicObjectiveCard{

    final static int VALUE = 2;
    final static int ROW = 4;
    final static int COLUMN = 5;
    final static int FIRST = 3;
    final static int SECOND = 4;

    public MediumShade34(){
        super("Sfumature medie", "Set di 3 & 4 ovunque");
    }


    public int calculateCardPoints(Pattern p){


        int f = 0;
        int s = 0;

        for( int i = 0 ; i < ROW ; i++ ) {
            for (int j = 0; j < COLUMN; j++) {
                if(p.getCell(i,j).getDie() != null && p.getCell(i,j).getDie().getNumber() == FIRST && p.getCell(i,j).isUsed()){
                    f++;
                }
                if(p.getCell(i,j).getDie() != null && p.getCell(i,j).getDie().getNumber() == SECOND && p.getCell(i,j).isUsed()){
                    s++;
                }

            }
        }

        if(f<s){
            return VALUE * f;
        }else {
            return VALUE * s;
        }
    }

}
