package it.polimi.ingsw.server.model.toolcard.Effect;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.exception.WrongResources;
import it.polimi.ingsw.exception.WrongResourcesType;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.RoundTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ChangePosition extends Effect {

    //public Game game;
    public ArrayList<String> effects;
    public ArrayList<Integer> values;


    //Setup the effect

    @Override
    public void useToolcard(Game game, Map<String, ArrayList<String>> effects, ArrayList<Integer> values) throws WrongResources, IllegalActionException {

        //this.game = game;
        this.effects = effects.get("ChangePosition");
        this.values = values;

        Pattern fromPattern = null;
        Pattern toPattern = null;
        RoundTrack roundTrack;
        ArrayList<Die> reserve = new ArrayList<>();
        Die d1 = null;
        Die d2 = null;




        if(this.effects.contains("PATTERN")){
            fromPattern = game.getCurrentRound().getActivePlayer().getPattern();
            d1 = getDieFromPattern(fromPattern, values.get(0)-1, values.get(1)-1);

        }

        if(this.effects.contains("RESERVE")){

            reserve = game.getCurrentRound().getReserve();
            if(reserve.size() == 0){
                throw new WrongResources(WrongResourcesType.NO_RESERVE.toString());
            }
            d1 = getDieFromReserve(game.getCurrentRound().getReserve(), values.get(0)-1);


        }


        if(this.effects.contains("ROUNDTRACK")){

            roundTrack = game.getRoundTrack();
            if(roundTrack == null){
                throw new WrongResources(WrongResourcesType.NO_ROUNDTRACK.toString());
            }
            System.out.println("Vedo se riesco a prendere un dado");
            d2 = getDieFromRoundTrack(roundTrack, values.get(1), values.get(2)-1);
            System.out.println("ok");

        }

        if(this.effects.contains("DICE BAG")){

            d2 = game.getBag().extractDie();
            d2.roll();

        }

        swapDice(game, d1, d2);



    }

    private Die getDieFromPattern(Pattern fromPattern, int y, int x) throws WrongResources {

        Die d;

        if(fromPattern.getCell(x,y).getDie() == null){
            throw  new WrongResources(WrongResourcesType.NO_DICE.toString());
        }else{
            d = fromPattern.getCell(x,y).getDie();
        }

        return d;

    }

    private Die getDieFromReserve(ArrayList<Die> reserve, int index) throws WrongResources, IllegalActionException{

        Die die;

        if(((index +1) > reserve.size()) || index < 0){
            throw  new WrongResources(WrongResourcesType.NO_DICE.toString());
        }else{
            die = reserve.get(index);
        }

        return die;

    }

    private Die getDieFromRoundTrack(RoundTrack roundTrack, int round, int index) throws WrongResources{

        Die die;
        System.out.println(index + " " + roundTrack.getDice(round).size());
        if(roundTrack.getDice(round) == null){
            throw new WrongResources(WrongResourcesType.NO_ROUNDTRACK.toString());
        }
        if(index + 1 > roundTrack.getDice(round).size()){
            throw  new WrongResources(WrongResourcesType.NO_DICE.toString());
        }else{
            die = roundTrack.getDice(round).get(index);
        }


        return die;
    }

    public void swapDice(Game game, Die d1, Die d2)throws IllegalActionException, WrongResources{

        if(this.effects.contains("ROUNDTRACK")) {
            game.getRoundTrack().getDice(values.get(1)).add(d1);
            game.getRoundTrack().getDice(values.get(1)).remove(d2);
        }

        if(this.effects.contains("RESERVE")){
            game.getCurrentRound().getReserve().add(d2);
            game.getCurrentRound().getReserve().remove(d1);
        }

        if(this.effects.contains("DICE BAG")){
            game.getBag().add(d1);
        }


    }

}
