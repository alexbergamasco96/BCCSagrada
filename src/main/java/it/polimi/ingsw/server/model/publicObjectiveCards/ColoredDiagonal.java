package it.polimi.ingsw.server.model.publicObjectiveCards;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utility.ColorANSI;

/*
 * created by alexbergamasco96 on 08/05/2018
 */

public class ColoredDiagonal extends PublicObjectiveCard {

    final static int VALUE = 1;
    final static int ROW = 4;
    final static int COLUMN = 5;

    public ColoredDiagonal(){
        super("Diagonali Colorate","Numero di dadi dello stesso colore diagonalmente adiacenti");
    }




    public int calculateCardPoints(Pattern p){

        int point = 0;
        int a,b,c,d;
        //Chech Red diagonals
        ColorANSI color = ColorANSI.ANSI_RED;


        for( int i = 0 ; i < ROW ; i++ ){
            for( int j = 0 ; j < ROW ; j++ ){
                if(p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == color && p.getCell(i, j).isUsed()) {
                    a = i - 1;
                    b = j + 1;
                    c = i + 1;
                    d = j + 1;
                    if (a >= 0 && a < ROW && b >= 0 && b < COLUMN) {
                        if (p.getCell(a, b).getDie() != null && p.getCell(a, b).getDie().getColor() == color && p.getCell(a, b).isUsed()) {
                            point++;
                        }
                    }
                    if (c >= 0 && c < ROW && d >= 0 && d < COLUMN) {
                        if (p.getCell(c, d).getDie() != null && p.getCell(c, d).getDie().getColor() == color && p.getCell(c, d).isUsed()) {
                            point++;
                        }
                    }
                }

            }
        }

        //Chech Red diagonals
        color = ColorANSI.ANSI_BLUE;


        for( int i = 0 ; i < ROW ; i++ ){
            for( int j = 0 ; j < ROW ; j++ ){
                if(p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == color && p.getCell(i, j).isUsed()) {
                    a = i - 1;
                    b = j + 1;
                    c = i + 1;
                    d = j + 1;
                    if (a >= 0 && a < ROW && b >= 0 && b < COLUMN) {
                        if (p.getCell(a, b).getDie() != null && p.getCell(a, b).getDie().getColor() == color && p.getCell(a, b).isUsed()) {
                            point++;
                        }
                    }
                    if (c >= 0 && c < ROW && d >= 0 && d < COLUMN) {
                        if (p.getCell(c, d).getDie() != null && p.getCell(c, d).getDie().getColor() == color && p.getCell(c, d).isUsed()) {
                            point++;
                        }
                    }
                }

            }
        }

        //Chech Red diagonals
        color = ColorANSI.ANSI_YELLOW;


        for( int i = 0 ; i < ROW ; i++ ){
            for( int j = 0 ; j < ROW ; j++ ){
                if(p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == color && p.getCell(i, j).isUsed()) {
                    a = i - 1;
                    b = j + 1;
                    c = i + 1;
                    d = j + 1;
                    if (a >= 0 && a < ROW && b >= 0 && b < COLUMN) {
                        if (p.getCell(a, b).getDie() != null && p.getCell(a, b).getDie().getColor() == color && p.getCell(a, b).isUsed()) {
                            point++;
                        }
                    }
                    if (c >= 0 && c < ROW && d >= 0 && d < COLUMN) {
                        if (p.getCell(c, d).getDie() != null && p.getCell(c, d).getDie().getColor() == color && p.getCell(c, d).isUsed()) {
                            point++;
                        }
                    }
                }

            }
        }

        //Chech Red diagonals
        color = ColorANSI.ANSI_PURPLE;


        for( int i = 0 ; i < ROW ; i++ ){
            for( int j = 0 ; j < ROW ; j++ ){
                if(p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == color && p.getCell(i, j).isUsed()) {
                    a = i - 1;
                    b = j + 1;
                    c = i + 1;
                    d = j + 1;
                    if (a >= 0 && a < ROW && b >= 0 && b < COLUMN) {
                        if (p.getCell(a, b).getDie() != null && p.getCell(a, b).getDie().getColor() == color && p.getCell(a, b).isUsed()) {
                            point++;
                        }
                    }
                    if (c >= 0 && c < ROW && d >= 0 && d < COLUMN) {
                        if (p.getCell(c, d).getDie() != null && p.getCell(c, d).getDie().getColor() == color && p.getCell(c, d).isUsed()) {
                            point++;
                        }
                    }
                }

            }
        }

        //Chech Red diagonals
        color = ColorANSI.ANSI_GREEN;


        for( int i = 0 ; i < ROW ; i++ ){
            for( int j = 0 ; j < ROW ; j++ ){
                if(p.getCell(i, j).getDie() != null && p.getCell(i, j).getDie().getColor() == color && p.getCell(i, j).isUsed()) {
                    a = i - 1;
                    b = j + 1;
                    c = i + 1;
                    d = j + 1;
                    if (a >= 0 && a < ROW && b >= 0 && b < COLUMN) {
                        if (p.getCell(a, b).getDie() != null && p.getCell(a, b).getDie().getColor() == color && p.getCell(a, b).isUsed()) {
                            point++;
                        }
                    }
                    if (c >= 0 && c < ROW && d >= 0 && d < COLUMN) {
                        if (p.getCell(c, d).getDie() != null && p.getCell(c, d).getDie().getColor() == color && p.getCell(c, d).isUsed()) {
                            point++;
                        }
                    }
                }

            }
        }


        return VALUE * point;


    }
}
