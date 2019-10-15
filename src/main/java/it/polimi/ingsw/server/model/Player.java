package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exception.IllegalActionException;

import it.polimi.ingsw.server.controller.ClientHandler;

import it.polimi.ingsw.utility.ColorANSI;


import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{

    private String nickname;
    private int token;
    private int point;
    private PrivateObjectiveCard privateObjectiveCard;
    private PatternCard[] patternCard;
    private Pattern pattern;
    private ColorANSI color;
    private ClientHandler clientHandler;
    private ArrayList<Integer> patternNumbersExtracted;
    private Boolean connected;
    private Die forcedDie;


    public Player(){

    }

    public Player(String username, ClientHandler clientHandler){
        this.nickname=username;
        this.clientHandler=clientHandler;
        patternCard = new PatternCard[2];
        connected=true;
        point = 0;
    }

    public void placeDie(Die d, int x, int y) throws IllegalActionException {
        pattern.placeDie(d,x,y);
    }



    public void calculatePoint(){

        System.out.println("PUNTEGGIO DI "+getNickname());
        point += privateObjectiveCard.calculateCardPoints(pattern);
        System.out.println("Punti della private "+point);
        point += token;
        System.out.println("Sommo i token "+point);
        point -= pattern.calculateEmptyCell();
        System.out.println("Tolgo celle vuote");
        if(point<0){
            point = 0;
        }
        System.out.println("Punteggio finale "+point);

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public ColorANSI getColor() {
        return color;
    }

    public void setColor(ColorANSI color) {
        this.color = color;
    }

    public PatternCard[] getPatternCard() {
        return patternCard;
    }

    public void setPatternCard(PatternCard[] patternCard) {
        this.patternCard = patternCard;
    }

    public ClientHandler getClientHandler() { return clientHandler; }

    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    public ArrayList<Integer> getPatternNumbersExtracted() {
        return patternNumbersExtracted;
    }

    public void setPatternNumbersExtracted(ArrayList<Integer> patternNumbersExtracted) {
        this.patternNumbersExtracted = patternNumbersExtracted;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Die getForcedDie() {
        return forcedDie;
    }

    public void setForcedDie(Die forcedDie) {
        this.forcedDie = forcedDie;
    }

    @Override
    public String toString() {
        return color.escape()+nickname+ColorANSI.RESET;
    }

    public void dump(){
        System.out.println(this.toString());
    }


}
