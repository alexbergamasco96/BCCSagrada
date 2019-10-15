package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.server.model.toolcard.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ClientGame implements Serializable {

    private ArrayList<PublicObjectiveCard> publicObjectiveCards;

    private ArrayList<ClientPlayer> players;

    private RoundTrack roundTrack;

    private ArrayList<Die> reserve;

    private String activePlayerUsername;

    private int roundNumber;

    private ArrayList<ToolCard> toolCards;

    private int turnNumber;



    //Constructor
    public ClientGame(){

    }


    public ClientGame(ArrayList<PublicObjectiveCard> publicObjectiveCards, ArrayList<ClientPlayer> players, RoundTrack roundTrack, ArrayList<Die> reserve, String activePlayerUsername, ArrayList<ToolCard> toolCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.players = players;
        this.roundTrack = roundTrack;
        this.reserve = reserve;
        this.activePlayerUsername = activePlayerUsername;
        this.roundNumber = 1;
        this.toolCards = toolCards;
        this.turnNumber=1;
        //toolCards = createToolCards();
    }




    public ArrayList<ToolCard> createToolCards(){


        ArrayList<ToolCard> toolCards = new ArrayList<>();

        ToolCard t1 = new ToolCard();
        ToolCard t2= new ToolCard();
        ToolCard t3 = new ToolCard();
        ToolCard t4 = new ToolCard();
        ToolCard t5 = new ToolCard();
        ToolCard t6 = new ToolCard();
        ToolCard t7 = new ToolCard();
        ToolCard t9 = new ToolCard();
        ToolCard t10 = new ToolCard();
        t1.newToolCard(1);
        t2.newToolCard(2);
        t3.newToolCard(3);
        t4.newToolCard(4);
        t5.newToolCard(5);
        t6.newToolCard(6);
        t7.newToolCard(7);
        t9.newToolCard(9);
        t10.newToolCard(10);
        toolCards.add(t1);
        toolCards.add(t2);
        toolCards.add(t3);
        toolCards.add(t4);
        toolCards.add(t5);
        toolCards.add(t6);
        toolCards.add(t7);
        toolCards.add(t9);
        toolCards.add(t10);
        return toolCards;

    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(ArrayList<PublicObjectiveCard> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }


    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public ArrayList<Die> getReserve() {
        return reserve;
    }

    public void setReserve(ArrayList<Die> reserve) {
        this.reserve = reserve;
    }

    public ArrayList<ClientPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<ClientPlayer> players) {
        this.players = players;
    }

    public String getActivePlayerUsername() {
        return activePlayerUsername;
    }

    public void setActivePlayerUsername(String activePlayerUsername) {
        this.activePlayerUsername = activePlayerUsername;
    }

    public ArrayList<Pattern> getOtherPlayersPatterns(String username){
        ArrayList<Pattern> otherPlayersPatterns = new ArrayList<>();
        for(ClientPlayer p:players){
            if (!p.getUsername().equals(username)){
                otherPlayersPatterns.add(p.getPattern());
            }
        }
        return otherPlayersPatterns;
    }

    public ArrayList<ClientPlayer> getOtherPlayers(String username){
        ArrayList<ClientPlayer> otherPlayers = new ArrayList<>();
        for(ClientPlayer p:players){
            if(!p.getUsername().equals(username)){
                otherPlayers.add(p);
            }
        }

        return otherPlayers;
    }

    public Pattern getYourPattern(String username){
        for(ClientPlayer p:players){
            if (p.getUsername().equals(username)){
                return p.getPattern();
            }
        }
        return null;
    }

    public ClientPlayer getPlayerFromName(String username){
        for (ClientPlayer p:players){
            if(p.getUsername().equals(username)){
                return p;
            }
        }
        return null;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getToolCardByNumber(int n){

        for(ToolCard t : toolCards){
            if(t.getNumber() == n){
                return toolCards.indexOf(t);
            }
        }

        return -1;

    }
}
