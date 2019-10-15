package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utility.ColorANSI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*
 * Created by FCaimi on 30/04/18
 */


// Servono le eccezioni per il caso in cui extractReserve(n) con n > bag.size ??

// Inizialmente i 90 Dadi sono "?" e vengono rollati solo all'estrazione della riserva, è ok ??

public class Bag implements Serializable{

    private ArrayList<Die> dice;

    public Bag(){
        dice = new ArrayList<Die>();
        for(int i=0;i<18;i++){
            dice.add(new Die(ColorANSI.ANSI_RED));
            dice.add(new Die(ColorANSI.ANSI_BLUE));
            dice.add(new Die(ColorANSI.ANSI_YELLOW));
            dice.add(new Die(ColorANSI.ANSI_GREEN));
            dice.add(new Die(ColorANSI.ANSI_PURPLE));
        }
    }

    /**
     * extracts dice from bag
     * @param n number of player
     * @return arraylist of extracted dice
     */

    public ArrayList<Die> extractReserve(int n){

        ArrayList<Die> reserve = new ArrayList<Die>();
        Random random = new Random();

        for(int i=0; i<n;i++){
            int index = random.nextInt(dice.size());
            reserve.add(dice.get(index));
            dice.remove(index);
        }

        for (Die d:reserve) {
            d.roll();
        }

        return reserve;
    }

    /**
     * extracts one die from bag
     * @return extracted die
     */

    public Die extractDie(){

        Random random = new Random();
        int index = random.nextInt(dice.size());

        Die d = this.dice.get(index);
        this.dice.remove(index);
        return d;

    }

    public void add(Die d){
        dice.add(d);
    }

    public int getSize(){
        return dice.size();
    }

    public void setDice(ArrayList<Die> dice) {
        this.dice = dice;
    }

    public ArrayList<Die> getDice() {
        return dice;
    }

    @Override
    public String toString() {
        String bag = new String();

        for (Die d : dice) {
            bag += d.toString()+" ";
        }

        return bag;
    }

    public void dump(){
        System.out.println(this.toString());
    }


}
