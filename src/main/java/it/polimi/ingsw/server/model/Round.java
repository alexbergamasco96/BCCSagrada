package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Created by FCaimi on 13/05/18
 */

public class Round implements Serializable{

    private int n;
    private int turn;
    private ArrayList<Die> reserve;
    private ArrayList<Player> players;
    private Player activePlayer;
    private ArrayList<Player> noTurnTwo;

    public Round(int n, ArrayList<Player> players){

        this.n = n;
        this.turn = 1;
        //this.players = players;
        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.activePlayer = players.get(0);
        this.noTurnTwo = new ArrayList<Player>();
    }


    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int n) {
        this.turn = n;
    }

    public ArrayList<Die> getReserve() {
        return reserve;
    }

    public void setReserve(ArrayList<Die> reserve) {
        this.reserve = reserve;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ArrayList<Player> getNoTurnTwo() { return noTurnTwo; }

    public void setNoTurnTwo(ArrayList<Player> noTurnTwo) { this.noTurnTwo = noTurnTwo; }

    /**
     * passes to round next turn
     * @return if there is a next turn
     */

    public boolean nextTurn(){

        if(getActivePlayer().getForcedDie()!= null){
            getActivePlayer().getPattern().forcedPlacement(getActivePlayer().getForcedDie());
            getReserve().remove(getActivePlayer().getForcedDie());
            getActivePlayer().setForcedDie(null);

        }

        if(turn<players.size()) {
            //System.out.println("index current player :   " + players.indexOf(activePlayer));
            activePlayer = players.get((players.indexOf(activePlayer) + 1)%players.size());
            turn++;
            return true;
        }
        else if( turn==players.size()){
            turn++;
            if(noTurnTwo.contains(activePlayer)){
                return nextTurn();
            }
            return true;
        }
        else if (turn>players.size() && turn<players.size()*2){
            activePlayer = players.get((players.indexOf(activePlayer) - 1)%players.size());
            turn++;
            if(noTurnTwo.contains(activePlayer)){
                return nextTurn();
            }
            return true;
        }
        else {
            return false;
        }

    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        String s = new String();
        for (Player player : players){
            if(player==activePlayer){
                s += "\033[4m"+player.toString()+"\033[0m ";
            }
            else{
                s += player.toString()+" ";
            }
        }
        return s;
    }

    public void dump(){
        System.out.println(this.toString());
    }
}
