package it.polimi.ingsw.server.model;


import java.io.Serializable;
import java.util.ArrayList;


public class Leaderboard implements Serializable {

    private ArrayList<Placement> placements;

    public Leaderboard(){
        placements = new ArrayList<>();
    }

    /**
     * sorts the array looking at the point (>)
     */

    public void sort(){

        for(int i = 0; i < placements.size() -1; i++) {
            int massimo = i;
            for(int j = i+1; j < placements.size(); j++) {

                //Qui avviene la selezione, ogni volta
                //che nell' iterazione troviamo un elemento piú piccolo di minimo
                //facciamo puntare minimo all' elemento trovato
                if(placements.get(massimo).getPoint() < placements.get(j).getPoint()) {
                    massimo = j;
                }
            }


            if(massimo!=i) {
                placements.add(i, placements.remove(massimo));
            }
        }

    }


    public ArrayList<Placement> getPlacements() {
        return this.placements;
    }

    /**
     * returns player placement
     * @param username player username
     * @return player placement
     */

    public Placement getPlayerPlacement(String username){
        for(Placement p : placements){
            if (p.getName().equals(username)){
                return p;
            }
        }
        return null;
    }

    public void setPlacements(ArrayList<Placement> placements) {
        this.placements = placements;
    }

    @Override
    public String toString(){

        String s = new String("");

        for(Placement p : placements){

            s += p.getName();
            s += ": ";
            s += p.getPoint();
            s += "\n";

        }

        return s;

    }

    public void dump(){


        System.out.println(this.toString());

    }


}
