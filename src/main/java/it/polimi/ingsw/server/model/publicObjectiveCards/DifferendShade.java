package it.polimi.ingsw.server.model.publicObjectiveCards;


import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;
import java.util.Arrays;

/*
 * Created by alexbergamasco96 on 06/05/18
 */

public class DifferendShade extends PublicObjectiveCard {

    final static int VALUE = 5;
    final static int ROW = 4;
    final static int COLUMN = 5;
    final static int ONE = 1;
    final static int TWO = 2;
    final static int THREE = 3;
    final static int FOUR = 4;
    final static int FIVE = 5;
    final static int SIX = 6;

    public DifferendShade(){
        super("Sfumature diverse","Set di dadi di ogni valore ovunque");
    }


    public int calculateCardPoints(Pattern p) {


        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == ONE && p.getCell(i, j).isUsed()) {
                    one++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == TWO && p.getCell(i, j).isUsed()) {
                    two++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == THREE && p.getCell(i, j).isUsed()) {
                    three++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == FOUR && p.getCell(i, j).isUsed()) {
                    four++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == FIVE && p.getCell(i, j).isUsed()) {
                    five++;
                }
                if (p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getNumber() == SIX && p.getCell(i, j).isUsed()) {
                    six++;
                }

            }
        }

        int arr[] = {one, two, three, four, five, six};
        Arrays.sort(arr);
        return VALUE * arr[0];

    }
}