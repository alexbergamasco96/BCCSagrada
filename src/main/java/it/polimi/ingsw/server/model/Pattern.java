package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.exception.IllegalActionExceptionType;

import java.io.Serializable;

/*
 * Created by alexbergamasco96 on 02/05/18
*/



public class Pattern implements Serializable{

    protected final static int ROW = 4;
    protected final static int COLUMN = 5;
    private String name;
    private int difficulty;
    private Cell[][] grid;
    private boolean firsPlace;


    public Pattern() {
        grid = new Cell[ROW][COLUMN];
        for(int i = 0; i < ROW ; i++) {
            for (int j = 0; j < COLUMN; j++) {
                grid[i][j] = new Cell(null, 0);
            }
        }
        firsPlace = false;
    }

    public String getName() { return this.name; }

    public void setName(String name)    { this.name = name; }

    public Cell getCell(int x, int y)   { return grid[x][y]; }

    public void setCell(Cell c, int x, int y)   { this.grid[x][y] = c; }

    public int getDifficulty()  { return this.difficulty; }

    public void setDifficulty(int d)    { this.difficulty = d; }

    /**
     * places die into the cell
     * @param d die
     * @param y cell y
     * @param x cell x
     * @throws IllegalActionException
     */

    public void placeDie(Die d, int y, int x) throws IllegalActionException {


        if(!firsPlace){
            if(y==0 || y==(COLUMN-1) || x== (ROW -1)  || x==0){

                if(!grid[x][y].placeDie(d)){

                    throw new IllegalActionException("Mossa non valida !");
                }
                firsPlace = true;

            }
            else{
                throw new IllegalActionException("Mossa non valida !");

            }
        }else {
            if(!checkProximity(x,y)){
                throw new IllegalActionException("Mossa non valida !");
            }
            checkProximitySameColor(d,y,x);
            checkProximitySameValue(d,y,x);
            if (!grid[x][y].placeDie(d)) {
                throw new IllegalActionException("Mossa non valida !");
            }
        }
    }

    /**
     * calculates number of empty cell
     * @return number of empty cell
     */

    public int calculateEmptyCell(){

        int n= 0;

        for(int i = 0 ; i < ROW-1 ; i ++){

            for (int j = 0 ; j < COLUMN-1 ; j++){

                if(!grid[i][j].isUsed()){
                    n ++;
                }

            }

        }

        return n;

    }

    /**
     * check if there is a die in near cells
     * @param x cell x
     * @param y cell y
     * @return
     */

    public boolean checkProximity(int x, int y){

        int i = x;
        int j = y;

        i = x - 1;
        if(i >= 0){
            if(getCell(i,j).isUsed()){
                return true;
            }

            j = y-1;
            if(j >= 0){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }
            j = y + 1;
            if(j < COLUMN){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }

        }


        i = x;
        j = y;
        if(true){
            if(getCell(i,j).isUsed()){
                return true;
            }
            j = y-1;
            if(j >= 0){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }
            j = y + 1;
            if(j < COLUMN){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }

        }

        i = x + 1;
        j = y;
        if(i < ROW){
            if(getCell(i,j).isUsed()){
                return true;
            }
            j = y-1;
            if(j >= 0){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }
            j = y + 1;
            if(j < COLUMN){
                if(getCell(i,j).isUsed()){
                    return true;
                }
            }

        }
        return false;

    }

    public void placeDieWithNoProximity(Die d, int y, int x) throws IllegalActionException {
        if(!firsPlace){
            if(y==0 || y==(COLUMN-1) || x==0 || x== (ROW -1)){
                if(!grid[x][y].placeDie(d)){
                    throw new IllegalActionException("Mossa non valida !");
                }
                firsPlace = true;

            }
            else{
                throw new IllegalActionException("Mossa non valida !");

            }
        }else {
            if(checkProximity(x,y)){
                throw new IllegalActionException("Mossa non valida !");
            }
            if (!grid[x][y].placeDie(d)) {
                throw new IllegalActionException("Mossa non valida !");
            }
        }
    }

    /**
     * checks if there is a die with same color near
     * @param d die
     * @param y cell y
     * @param x cell x
     * @throws IllegalActionException
     */

    public void checkProximitySameColor(Die d, int y, int x) throws IllegalActionException{

        int i= x;
        int j = y;

        //Cella superiore
        i = x-1;

        if(i>=0){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getColor().equals(d.getColor())) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso colore");
                }
            }
        }

        //Cella inferiore
        i = x+1;

        if(i<ROW){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getColor().equals(d.getColor())) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso colore");
                }
            }
        }

        //Cella sx
        i=x;
        j=y-1;

        if(j>=0){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getColor().equals(d.getColor())) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso colore");
                }
            }
        }

        //Cella dx
        j = y+1;

        if(j<COLUMN){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getColor().equals(d.getColor())) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso colore");
                }
            }
        }

    }

    /**
     * checks if there is a die with same number near
     * @param d die
     * @param y cell y
     * @param x cell x
     * @throws IllegalActionException
     */

    public void checkProximitySameValue(Die d, int y, int x) throws IllegalActionException{

        int i= x;
        int j = y;

        //Cella superiore
        i = x-1;

        if(i>=0){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getNumber() == d.getNumber()) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso numero");
                }
            }
        }

        //Cella inferiore
        i = x+1;

        if(i<ROW){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getNumber() == d.getNumber()) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso numero");
                }
            }
        }

        //Cella sx
        i=x;
        j=y-1;

        if(j>=0){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getNumber() == d.getNumber()) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso numero");
                }
            }
        }

        //Cella dx
        j = y+1;

        if(j<COLUMN){
            if(getCell(i,j).isUsed()){
                if(getCell(i,j).getDie().getNumber() == d.getNumber()) {
                    throw new IllegalActionException("Mossa non valida !  Piazzamento adiacente a un dado dello stesso numero");
                }
            }
        }

    }


    public String getRow(int n){

        String row = "";

        for(int j = 0; j < COLUMN ; j++) {
            row += this.grid[n][j].toString();
        }

        return row;


    }

    /**
     * checks if it is possible to place the forced die
     * @param d forced die
     * @return result
     */

    public boolean checkForcedPlacement(Die d){

        for(int i = 0; i < ROW ; i ++){
            for(int j = 0; j < COLUMN ; j++){

                try{
                    placeDie(d,j,i);
                    getCell(i,j).freeCell();
                    return true;
                }catch (Exception e){

                }

            }

        }
        return false;

    }

    public boolean isFirsPlace() {
        return firsPlace;
    }

    public void setFirsPlace(boolean firsPlace) {
        this.firsPlace = firsPlace;
    }

    /**
     * executes forced placement
     * @param d die
     * @return result
     */

    public boolean forcedPlacement(Die d){

        for(int i = 0; i < ROW ; i ++){
            for(int j = 0; j < COLUMN ; j++){

                try{
                    placeDie(d,j,i);

                    return true;
                }catch (Exception e){

                }

            }

        }
        return false;
    }

    @Override
    public String toString(){

        String pattern = new String();
        pattern += "\t";
        pattern += "[";
        pattern += name;
        pattern += "]";
        pattern += "\n";
        for(int i = 0; i < ROW ; i++){
            for(int j = 0; j < COLUMN ; j++) {
                pattern += this.grid[i][j].toString();
            }
            pattern += "\n";
        }

        return pattern;
    }

    public void dump() {    System.out.println(this.toString()); }





}
