package it.polimi.ingsw.server.model.toolcard;

import it.polimi.ingsw.exception.IllegalActionException;
import it.polimi.ingsw.exception.IllegalActionExceptionType;
import it.polimi.ingsw.exception.WrongResources;
import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.toolcard.Effect.*;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GsonReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/*
 * Created by FCaimi on 10/05/18
 */

public class ToolCard extends Card implements Serializable {

    private boolean used;
    private ColorANSI color;
    private Map<String, ArrayList<String>> effects;
    private Effect effect;
    private ArrayList<String> toDo;
    private int number;


    public ToolCard(){
        used = false;
        toDo = new ArrayList<>();
    }
    public ToolCard(String name, String description, ColorANSI color){
        super(name, description);

        this.color=color;
        used = false;
        toDo = new ArrayList<>();


    }

    public void newToolCard(int toolCardNumber){

        GsonReader gsonReader = new GsonReader();

        ToolCard toolCard = (ToolCard)gsonReader.readObject("src/main/resources/json/toolcard/"+toolCardNumber+".json", ToolCard.class);

        this.setName(toolCard.getName());
        this.setDescription(toolCard.getDescription());
        this.setColor(toolCard.getColor());
        this.setEffects(toolCard.getEffects());
        this.setNumber(toolCard.getNumber());
        this.setToDo(toolCard.getToDo());

        if(this.effects.containsKey("ChangePosition")){
            this.effect = new ChangePosition();
        }
        if(this.effects.containsKey("ChangeValue")){
            this.effect = new ChangeValue();
        }
        if(this.effects.containsKey("ModifyTurn")){
            this.effect = new ModifyTurn();
        }
        if(this.effects.containsKey("Move")){
            this.effect = new Move();
        }

    }

    /**
     * actives toolcard power
     * @param game current game
     * @param values parameters needed from the toolcard
     * @throws IllegalActionException
     * @throws WrongResources
     */

    public void useToolCard(Game game, ArrayList<Integer> values )throws IllegalActionException, WrongResources{

        //Check player's tokens

        int i = 0;
        if(isUsed()){
            i = 2;
        }else {
            i = 1;
        }

        if(game.getCurrentRound().getActivePlayer().getToken() < i){
            throw new IllegalActionException(IllegalActionExceptionType.NOT_ENOUGH_TOKEN.toString());
        }


        effect.useToolcard(game, effects, values);
        setUsed();
        game.getCurrentRound().getActivePlayer().setToken(game.getCurrentRound().getActivePlayer().getToken()-i);

    }


    public boolean isUsed() {
        return used;
    }

    public void setUsed(){
        used=true;
    }

    public ColorANSI getColor() {
        return color;
    }

    public void setColor(ColorANSI color) {
        this.color = color;
    }

    public Map<String, ArrayList<String>> getEffects() {
        return effects;
    }

    public void setEffects(Map<String, ArrayList<String>> effects) {
        this.effects = effects;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getToDo() {
        return toDo;
    }

    public void setToDo(ArrayList<String> toDo) {
        this.toDo = toDo;
    }

    /**
     * gets toolcards cost
     * @return toolcard cost
     */

    public int tokenToUse(){
        if(isUsed()){
            return 2;
        }else{
            return 1;
        }
    }

}
