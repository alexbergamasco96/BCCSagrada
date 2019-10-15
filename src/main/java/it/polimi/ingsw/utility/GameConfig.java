package it.polimi.ingsw.utility;



public class GameConfig {

    private int SOCKET_PORT;
    private int RMI_PORT;
    private int MAX_PLAYER ;
    private int NUM_PATTERN ;
    private int NUM_PUBLIC ;
    private int TIMEOUT ; //5*1000
    private int PATTERNCHOICE_TIMEOUT ;
    private int TURN_TIMEOUT ;
    private int TOOLCARDS_NUMBER ;



    public GameConfig(int SOCKET_PORT, int RMI_PORT, int MAX_PLAYER, int NUM_PATTERN, int NUM_PUBLIC, int TIMEOUT, int PATTERNCHOICE_TIMEOUT, int TURN_TIMEOUT, int TOOLCARDS_NUMBER) {
        this.SOCKET_PORT = SOCKET_PORT;
        this.RMI_PORT = RMI_PORT;
        this.MAX_PLAYER = MAX_PLAYER;
        this.NUM_PATTERN = NUM_PATTERN;
        this.NUM_PUBLIC = NUM_PUBLIC;
        this.TIMEOUT = TIMEOUT;
        this.PATTERNCHOICE_TIMEOUT = PATTERNCHOICE_TIMEOUT;
        this.TURN_TIMEOUT = TURN_TIMEOUT;
        this.TOOLCARDS_NUMBER = TOOLCARDS_NUMBER;
    }

    public GameConfig() {

    }

    public void setGameConfig(){

        GsonReader gsonReader = new GsonReader();

        GameConfig gameConfig = (GameConfig)gsonReader.readObject("src/main/resources/json/GameConfiguration.json",GameConfig.class);

        this.SOCKET_PORT = gameConfig.getSOCKET_PORT();
        this.RMI_PORT = gameConfig.getRMI_PORT();
        this.MAX_PLAYER = gameConfig.getMAX_PLAYER();
        this.NUM_PATTERN = gameConfig.getNUM_PATTERN();
        this.NUM_PUBLIC = gameConfig.getNUM_PUBLIC();
        this.TIMEOUT = gameConfig.getTIMEOUT();
        this.PATTERNCHOICE_TIMEOUT = gameConfig.getPATTERNCHOICE_TIMEOUT();
        this.TURN_TIMEOUT = gameConfig.getTURN_TIMEOUT();
        this.TOOLCARDS_NUMBER = gameConfig.getTOOLCARDS_NUMBER();
    }

    public int getSOCKET_PORT() {
        return SOCKET_PORT;
    }

    public int getRMI_PORT() {
        return RMI_PORT;
    }

    public int getMAX_PLAYER() {
        return MAX_PLAYER;
    }

    public int getNUM_PATTERN() {
        return NUM_PATTERN;
    }

    public int getNUM_PUBLIC() {
        return NUM_PUBLIC;
    }

    public int getTIMEOUT() {
        return TIMEOUT;
    }

    public int getPATTERNCHOICE_TIMEOUT() {
        return PATTERNCHOICE_TIMEOUT;
    }

    public int getTURN_TIMEOUT() {
        return TURN_TIMEOUT;
    }

    public int getTOOLCARDS_NUMBER() {
        return TOOLCARDS_NUMBER;
    }



}
