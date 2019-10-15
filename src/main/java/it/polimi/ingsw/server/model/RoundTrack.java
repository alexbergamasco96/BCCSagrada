package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exception.OutOfRoundTrackException;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Created by FCaimi on 13/05/18
 *
 */

public class RoundTrack implements Serializable{

    private ArrayList<ArrayList<Die>> track ;

    private static final int MAX_ROUND = 10;

    public RoundTrack(){
        track = new ArrayList<ArrayList<Die>>();
        for(int i = 0;i<MAX_ROUND;i++){
            track.add(new ArrayList<Die>());

        }
    }

    /**
     * inserts die into the roundTrack
     * @param round round number
     * @param die die
     * @throws OutOfRoundTrackException
     */

    public void insertDie(int round, Die die) throws OutOfRoundTrackException {
        if(round <= MAX_ROUND) {
            track.get(round-1).add(die);
        }
        else{
            throw new OutOfRoundTrackException();
        }
    }

    public ArrayList<Die> getDice(int round){
        return track.get(round-1);
    }

    public ArrayList<ArrayList<Die>> getTrack() {
        return track;
    }

    @Override
    public String toString() {
        String s = new String();
        for (ArrayList<Die> round:track) {
            s+="Round "+(track.indexOf(round)+1)+" : ";
            for (Die d:round) {
                s+=d.toString();
            }
            s+="\n";
        }
        return s;
    }

    public void dump(){
        System.out.println(toString());
    }
}
