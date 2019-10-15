package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exception.GameFullException;
import it.polimi.ingsw.exception.OutOfRoundTrackException;
import it.polimi.ingsw.server.model.publicObjectiveCards.*;
import it.polimi.ingsw.server.model.toolcard.ToolCard;
import it.polimi.ingsw.utility.ColorANSI;
import it.polimi.ingsw.utility.GameConfig;
import it.polimi.ingsw.utility.GsonReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable {

    private ArrayList<Player> players;
    private ArrayList<ColorANSI> playerColors;
    private ArrayList<Round> rounds;
    private Round currentRound;
    private Bag bag;
    private ArrayList<Integer> patterns;
    private ArrayList<PrivateObjectiveCard> privateObjectiveCards;
    private ArrayList<PublicObjectiveCard> publicObjectiveCards;
    private ArrayList<ToolCard> toolCards;
    private GameConfig gameConfig;
    private RoundTrack roundTrack;
    private Leaderboard leaderboard;

    GsonReader gr = new GsonReader();

    public Game(){
        players = new ArrayList<Player>();
        playerColors = new ArrayList<ColorANSI>();
        gameConfig = new GameConfig();
        gameConfig.setGameConfig();

        playerColors.add(ColorANSI.ANSI_BLUE);
        playerColors.add(ColorANSI.ANSI_RED);
        playerColors.add(ColorANSI.ANSI_GREEN);
        playerColors.add(ColorANSI.ANSI_YELLOW);
        playerColors.add(ColorANSI.ANSI_PURPLE);
        rounds = new ArrayList<Round>();
        bag = new Bag();
        patterns = new ArrayList<Integer>();
        /*for(int i = 0; i<=gameConfig.getNUM_PATTERN()/2; i++){
            patterns.add(i);
        }*/
        for(int i = 0; i<=gameConfig.getNUM_PATTERN(); i++){
            patterns.add(i);
        }

        privateObjectiveCards = new ArrayList<PrivateObjectiveCard>();
        privateObjectiveCards.add((PrivateObjectiveCard)gr.readObject("src/main/resources/json/privateObjectiveCard/SfumatureRosse.json", PrivateObjectiveCard.class));
        privateObjectiveCards.add((PrivateObjectiveCard)gr.readObject("src/main/resources/json/privateObjectiveCard/SfumatureGialle.json", PrivateObjectiveCard.class));
        privateObjectiveCards.add((PrivateObjectiveCard)gr.readObject("src/main/resources/json/privateObjectiveCard/SfumatureVerdi.json", PrivateObjectiveCard.class));
        privateObjectiveCards.add((PrivateObjectiveCard)gr.readObject("src/main/resources/json/privateObjectiveCard/SfumatureBlu.json", PrivateObjectiveCard.class));
        privateObjectiveCards.add((PrivateObjectiveCard)gr.readObject("src/main/resources/json/privateObjectiveCard/SfumatureViola.json", PrivateObjectiveCard.class));
        publicObjectiveCards = new ArrayList<PublicObjectiveCard>();

        toolCards = new ArrayList<ToolCard>();
        toolCards = extractToolCards();
        roundTrack = new RoundTrack();

    }

    /**
     * add new player into the game
     * @param p player
     * @throws GameFullException
     */

    public void addPlayer(Player p) throws GameFullException{
        System.out.println("Entrato in game");
        if(players.size()>=gameConfig.getMAX_PLAYER()){
            System.out.println("Partita piena");
            throw new GameFullException("La partita è piena !");
        }
        Random random = new Random();
        int index = random.nextInt(playerColors.size());
        p.setColor(playerColors.remove(index));
        players.add(p);
        System.out.println(players);
    }

    /**
     * extracts new reserve
     * @param playerNumber number of player
     * @return array containing dice of new reserve
     */

    public ArrayList<Die> extractReserve(int playerNumber){
        currentRound.setReserve(bag.extractReserve(playerNumber*2+1));
        return currentRound.getReserve() ;
    }

    /**
     * extracts three public cards
     */

    public void extractPublicObjectiveCard() {
        ArrayList<Integer> n = new ArrayList<Integer>();
        for(int i=1; i<gameConfig.getNUM_PUBLIC(); i++){
            n.add(i);
        }
        Random random = new Random();
        for(int i=0; i<3; i++){
            int index = random.nextInt(n.size());
            switch (n.get(index)){
                case 0:
                    publicObjectiveCards.add(new RowColorVariety());
                    break;
                case 1:
                    publicObjectiveCards.add(new ColumnColorVariety());
                    break;
                case 2:
                    publicObjectiveCards.add(new RowShadeVariety());
                    break;
                case 3:
                    publicObjectiveCards.add(new ColumnShadeVariety());
                    break;
                case 4:
                    publicObjectiveCards.add(new LightShade12());
                    break;
                case 5:
                    publicObjectiveCards.add(new MediumShade34());
                    break;
                case 6:
                    publicObjectiveCards.add(new DarkShade56());
                    break;
                case 7:
                    publicObjectiveCards.add(new DifferendShade());
                    break;
                case 8:
                    publicObjectiveCards.add(new ColoredDiagonal());
                    break;
                case 9:
                    publicObjectiveCards.add(new ColorVariety());
                    break;
            }
            n.remove(n.get(index));
        }
    }

    /**
     * extracts one private card for each player
     */

    public void extractPrivateObjectiveCard() {
        Random random = new Random();
        for(Player p: players){
            int index = random.nextInt(privateObjectiveCards.size());
            System.out.println(index);
            index = random.nextInt(privateObjectiveCards.size());
            p.setPrivateObjectiveCard(privateObjectiveCards.get(index));
            privateObjectiveCards.remove(privateObjectiveCards.get(index));
        }
    }

    /**
     * extracts four pattern for player
     * @return array containing pattern number
     */

    public ArrayList<Integer> extractPlayerPatterns() {
        ArrayList<Integer> fourPatterns = new ArrayList<Integer>();
        for(int i = 0; i<4;i++) {

            Random random = new Random();
            int index = random.nextInt(patterns.size()-1);
            index ++;
            int pattern = patterns.get(index);
            patterns.remove(index);
            fourPatterns.add(pattern);
        }

        return fourPatterns;

    }

    /**
     * sets up rounds looking at the number of players
     * @param activePlayers number of players
     */

    public void setUp(ArrayList<Player> activePlayers){
        for (int i =1; i<11; i++){
            rounds.add(new Round(i,activePlayers));
            activePlayers.add(activePlayers.remove(0));
        }

        for(Round r : rounds){
            r.dump();
        }
        currentRound = rounds.get(0);
        currentRound.dump();

    }

    /**
     * passes to next round
     * @return result
     */

    public boolean nextRound(){
        if(rounds.indexOf(currentRound)+1<rounds.size()) {
            for(Die d : currentRound.getReserve()){
                try{
                    roundTrack.insertDie(currentRound.getN(), d);
                }catch (OutOfRoundTrackException e){
                    System.out.println(e.getMessage());
                }
            }
            currentRound = rounds.get(rounds.indexOf(currentRound) + 1);

            return true;
        }
        else{
            return false;
        }
    }

    /**
     * passes to next turn
     * @return result
     */

    public boolean nextTurn(){
        if(!currentRound.nextTurn()){
            return nextRound();
        }
        return true;
    }

    /**
     * gets player from name
     * @param username player username
     * @return the player
     */

    //Return the player with this username
    public Player getPlayerFromName(String username){

        for(Player p : this.players){
            if(p.getNickname().equals(username)){
                return p;
            }
        }
        //sistema con una eccezione
        return null;

    }

    /**
     * calculates all players points
     */

    public void calculatePoint(){

        this.leaderboard = new Leaderboard();
        for(Player p : players){
            p.calculatePoint();
            for(PublicObjectiveCard poc : publicObjectiveCards){

                p.setPoint(p.getPoint() +  poc.calculateCardPoints(p.getPattern()));

            }
            leaderboard.getPlacements().add(new Placement(p.getNickname(), p.getPoint()));
        }
        leaderboard.dump();
        leaderboard.sort();
        leaderboard.dump();

    }

    /**
     * extracts three tool cards
     * @return array  of three toolcards
     */

    public ArrayList<ToolCard> extractToolCards(){

        ArrayList<ToolCard> toolCards = new ArrayList<>();

        ArrayList<Integer> n = new ArrayList<Integer>();
        for(int i=1; i<=gameConfig.getTOOLCARDS_NUMBER(); i++){
            n.add(i);
        }
        Random random = new Random();
        for(int i=0; i<3; i++) {
            int index = random.nextInt(n.size());
            ToolCard toolCard = new ToolCard();
            toolCard.newToolCard(n.get(index));
            toolCards.add(toolCard);
            n.remove(n.get(index));
        }

        return toolCards;

    }


    public int playersSize(){
        return players.size();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<ColorANSI> getPlayerColors() {
        return playerColors;
    }

    public void setPlayerColors(ArrayList<ColorANSI> playerColors) {
        this.playerColors = playerColors;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public ArrayList<Integer> getPatterns() {
        return patterns;
    }

    public void setPatterns(ArrayList<Integer> patterns) {
        this.patterns = patterns;
    }

    public ArrayList<PrivateObjectiveCard> getPrivateObjectiveCards() {
        return privateObjectiveCards;
    }

    public void setPrivateObjectiveCards(ArrayList<PrivateObjectiveCard> privateObjectiveCards) {
        this.privateObjectiveCards = privateObjectiveCards;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(ArrayList<PublicObjectiveCard> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }



    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }


}
