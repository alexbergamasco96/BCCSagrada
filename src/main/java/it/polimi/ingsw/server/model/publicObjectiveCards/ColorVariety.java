package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utility.ColorANSI;

import java.util.Arrays;

/*
 * Created by alexbergamasco96 on 06/05/18
 */

public class ColorVariety extends PublicObjectiveCard {

    final static int VALUE = 4;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public ColorVariety(){
        super("Varietà di Colore", "Set di dadi di ogni colore ovunque");
    }


    public int calculateCardPoints(Pattern p){
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == ColorANSI.ANSI_RED && p.getCell(i, j).isUsed()) {
                    one++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == ColorANSI.ANSI_BLUE && p.getCell(i, j).isUsed()) {
                    two++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == ColorANSI.ANSI_GREEN && p.getCell(i, j).isUsed()) {
                    three++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == ColorANSI.ANSI_YELLOW && p.getCell(i, j).isUsed()) {
                    four++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == ColorANSI.ANSI_PURPLE && p.getCell(i, j).isUsed()) {
                    five++;
                }


            }
        }

        int arr[] = {one, two, three, four, five};
        Arrays.sort(arr);
        return arr[0] * VALUE;

    }

}
