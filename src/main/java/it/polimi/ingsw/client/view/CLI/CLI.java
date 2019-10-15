package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.Client;
import it.polimi.ingsw.client.controller.ClientGame;
import it.polimi.ingsw.client.controller.ClientPlayer;
import it.polimi.ingsw.client.controller.SocketClient.ClientSocket;
import it.polimi.ingsw.client.controller.RMIClient.RMIClient;
import it.polimi.ingsw.client.view.ClientObserver;
import it.polimi.ingsw.client.view.UserInterfaceController;
import it.polimi.ingsw.exception.AlreadyUsedUsernameException;
import it.polimi.ingsw.exception.ExitException;
import it.polimi.ingsw.server.model.Die;
import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.server.model.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.publicObjectiveCards.PublicObjectiveCard;
import it.polimi.ingsw.utility.BestScore;
import it.polimi.ingsw.utility.ColorANSI;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CLI implements UserInterfaceController, ClientObserver {

    private final static int SOCKETPORT=1996;
    private final static int RMIPORT = 1099;


    private Client client;

    private Scanner scan;

    private ToUser toUser;

    private BlockingQueue<String> input;

    private ConsoleReader consoleReader;

    private boolean read;

    private BlockingQueue<String> toDo;

    private ArrayList<Pattern> patterns = new ArrayList<>();

    private TurnManager turnManager;






    public CLI(){

        scan = new Scanner(System.in);
        toUser = new ToUser();
        read = false;
        input = new ArrayBlockingQueue<>(1);
        toDo = new ArrayBlockingQueue<>(5);
        turnManager = new TurnManager();
        turnManager.start();



    }


    private class TurnManager extends Thread {



        public void run(){

            while(true) {
                if (toDo.size() != 0) {

                    try {

                        String action = toDo.take();

                        if (action.equals("PATTERN")) {
                            choosePattern(patterns);
                        }
                        if (action.equals("YOUR TURN")) {
                            performTurn(client.getClientGame());
                        }
                        if (action.equals("NOT YOUR TURN")) {
                            onNotYourTurn();
                        }
                        if (action.equals("TOOL 11")){
                            die11();
                        }

                    } catch (InterruptedException e) {
                        System.out.println("Timeout scaduto");
                    } catch(ExitException e){
                        toDo.add("YOUR TURN");
                    }

                }
            }



        }

    }

    public void startCLI() {

        String connection;
        String address;



        //request Connection
        toUser.request("Quale connessione vuoi utilizzare?");
        toUser.request("1) SOCKET \n2) RMI");

        connection = scan.nextLine();

        while(!connection.equals(ConnectionType.SOCKET.toString()) && !connection.equals(ConnectionType.RMI.toString())){

            toUser.request("Quale connessione vuoi utilizzare?");
            toUser.request("1) SOCKET \n2) RMI");
            connection = scan.nextLine();

        }


        toUser.request("Inserire indirizzo di rete:");
        address = scan.nextLine();

        if(connection.equals(ConnectionType.SOCKET.toString())){
            try {
                client = new ClientSocket(address, SOCKETPORT);
            } catch (Exception e) {
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                startCLI();
                return;
            }
            client.addObserver(this);
        }else if (connection.equals(ConnectionType.RMI.toString())){
            client = new RMIClient(address, RMIPORT);
            try {
                client.connectToServer();
            }catch (Exception e){
                onExceptionLaunched(e.getClass().getSimpleName(),e.getMessage());
                startCLI();
                return;
            }
            client.addObserver(this);
        }


        try {
            authentication();
        }catch (Exception e){
            e.printStackTrace();
        }





    }




    public void authentication(){

        String authentication;

        toUser.request("Digita 'login' per accedere alla partita con un account esistente oppure 'signin' per registrarti");

        authentication = scan.nextLine();

        while(!authentication.toLowerCase().equals(AuthenticationType.LOGIN.toString()) && !authentication.toLowerCase().equals(AuthenticationType.SIGNIN.toString())){

            toUser.error("Comando non valido");
            toUser.request("Digita 'login' per accedere alla partita con un account esistente oppure 'signin' per registrarti");
            authentication = scan.nextLine();

        }

        if(authentication.equals(AuthenticationType.LOGIN.toString())){
            try{
                loginAction();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

                signIn();
        }

    }


    public void loginAction() throws Exception{


        String username;
        String password;



        toUser.request("Digitare username:");
        username = scan.nextLine();

        toUser.request("Digitare password:");
        password = scan.nextLine();


        client.login(username, password);
        synchronized (this){
            wait(500);
        }
        if(!client.isLogged()){

            authentication();

        }else {
            read = false;
            consoleReader = new ConsoleReader(this);
            consoleReader.start();
        }

    }

    public void signIn(){


        String username;
        String password;



        toUser.request("Digitare username:");
        username = scan.nextLine();

        toUser.request("Digitare password:");
        password = scan.nextLine();

        try{
            client.signIn(username, password);
        }catch (AlreadyUsedUsernameException e){
            onExceptionLaunched(e.getClass().getSimpleName(), e.getMessage());
            authentication();
        }

        authentication();

    }

    public void signInAction() throws Exception{

    }

    @Override
    public void startWaitingAlert(String header, String message) throws Exception {

    }

    public void startWaitingAlert() throws Exception{

    }

    public void startErrorDialog(String errorMessage) throws Exception{

        toUser.error(errorMessage);

    }

    private void choosePattern(ArrayList<Pattern> patterns) throws InterruptedException, ExitException{

        toUser.request("Seleziona il numero di uno dei seguenti PATTERN per iniziare la partita");
        System.out.println("\n");
        int i = 0;
        for(Pattern p : patterns ){
            i++;
            System.out.println(i + ")");
            p.dump();
            System.out.println("Difficoltà : " +p.getDifficulty() + "\n");

        }

        read = true;
        String val;
        int flag = 0;
        int p = 0;


        while(flag == 0 ){

            val = takeInput();

            try{
                p = Integer.parseInt(val);
            }catch(NumberFormatException e){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }

            if(p > patterns.size() || p < 1){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }else{
                flag = 1;
            }

        }

        read = false;
        toUser.sendDescription();
        client.patternSelected(patterns.get(p-1));


    }
    

    @Override
    public void onSuccessfullLogin(String username) {

        toUser.notify(username + " connesso");

    }

    @Override
    public void onSuccessfullSignIn(String username) {

        toUser.notify("Registrazione avvenuta con successo. Ora puoi loggarti, "+username);
    }

    @Override
    public void onExceptionLaunched(String exceptionName, String errorMessage) {

        toUser.error("Exception Launched : "+exceptionName+" -> " + errorMessage);

    }


    @Override
    public void onSetUpReceived(PrivateObjectiveCard pr, ArrayList<PublicObjectiveCard> pu, ArrayList<Pattern> pa) {

        for(int i = 0; i < pu.size() ; i ++){
            System.out.println(ColorANSI.ANSI_GREEN.escape() + "CARTA OBIETTIVO PUBBLICO " + (i+1) + ": " + pu.get(i).getName() + ColorANSI.RESET + ColorANSI.ANSI_YELLOW.escape() +"\nDescrizione: " + pu.get(i).getDescription()+ColorANSI.RESET);
        }

        System.out.println(ColorANSI.ANSI_RED.escape() + "CARTA OBIETTIVO PRIVATO "+ pr.getName()+ ColorANSI.RESET + ColorANSI.ANSI_YELLOW.escape()+"\nDescrizione: "+pr.getDescription()+ColorANSI.RESET);

        this.patterns = pa;
        toDo.add("PATTERN");

    }

    @Override
    public void onPatternReceived(ArrayList<ClientPlayer> player, String username) {

        int i;

        for( i = 0 ; i < player.size() ; i++ ){

            if(player.get(i).getUsername().equals(username)){
                break;
            }

        }



        toUser.notify("Il tuo pattern: ");
        toUser.notify(player.get(i).getPattern().toString());
        toUser.notify("I pattern degli altri giocatori: ");

        for(ClientPlayer p : player){
            if(!p.getUsername().equals(username)) {
                toUser.notify(p.getUsername());
                toUser.notify(p.getPattern().toString());
            }

        }


    }





    @Override
    public synchronized void onClientGameReceived(ClientGame clientGame) {

        toUser.sendBoardGame(clientGame, client.getUsername(), client.getPrivateObjectiveCard());

        if(!clientGame.getActivePlayerUsername().equals(client.getUsername())){
            if(read){
                //System.out.println("Timer scaduto");
                //consoleReader.pause();
            }
            read = false;
            toDo.add("NOT YOUR TURN");

        }else{
            read = false;
            toDo.add("YOUR TURN");
            read = true;
        }


    }

    public void isYourTurn(ClientGame clientGame){

        toDo.add("YOUR TURN");

    }

    private void performTurn(ClientGame clientGame) throws InterruptedException, ExitException{

        if(clientGame.getActivePlayerUsername().equals(client.getUsername())){
            toUser.notify("E' IL TUO TURNO");

            String in = "";
            read = true;
            do{

                toUser.request("Selezionare [1] per effettuare un piazzamento dado, [2] per utilizzare una delle carte strumento, [3] per terminare il proprio turno");
                in = takeInput();

            }while (!in.equals("1")&&!in.equals("2")&&(in.equals("1")&&client.isActionMoveDone())&&(in.equals("2")&&client.isActionToolCardDone())&&!in.equals("3"));
            read = false;
            switch (in){
                case "1":
                        moveDieAction();
                    break;
                case "2":
                    toolCardAction();
                    break;
                case "3":
                    client.endTurn();
                    break;
                default:
                    toDo.add("YOUR TURN");
                    break;

            }
        }

    }

    @Override
    public void moveDieAction() throws ExitException, InterruptedException{

        toUser.request("Selezionare il dado da prelevare dalla riserva");
        toUser.sendReserve(client.getClientGame().getReserve());

        String val = "";
        int flag = 0;
        int reserveIndex = 0;
        read = true;

        do{

            val = takeInput();
            try{
                reserveIndex = Integer.parseInt(val);
            }catch(NumberFormatException e){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }

            if(reserveIndex > client.getClientGame().getReserve().size() || reserveIndex < 1){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }else{
                flag = 1;
            }

        }while(flag == 0 );

        reserveIndex--;

        toUser.request("Ora seleziona la colonna nella quale vuoi piazzare il dado");

        String val2;
        int flag2 = 0;
        int x = 0;

        do{

            val2 = takeInput();
            try{
                x = Integer.parseInt(val2);
            }catch(NumberFormatException e){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }

            if(x > 5 || x < 1){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }else{
                flag2 = 1;
            }

        }while(flag2 == 0 );

        x--;

        toUser.request("Ora seleziona la riga nella quale vuoi piazzare il dado");

        String val3;
        int flag3 = 0;
        int y = 0;

        do{

            val3 = takeInput();
            try{
                y = Integer.parseInt(val3);
            }catch(NumberFormatException e){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }

            if(y > 4 || y < 1){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }else{
                flag3 = 1;
            }

        }while(flag3 == 0 );

        y--;

        read = false;

        client.moveDie(reserveIndex, x, y);



    }







    @Override
    public void onNotYourTurn() {

        toUser.notify("Attendere il proprio turno. Ora è il turno di " + client.getClientGame().getActivePlayerUsername());

    }

    @Override
    public void onEndGame(Leaderboard leaderboard, ArrayList<BestScore> scores) {

        toUser.sendLeaderBoard(leaderboard);
        toUser.sendBestScores(scores);

    }

    @Override
    public void onForcedPatternChoice() {
       
    }

    public void toolCardAction() throws InterruptedException, ExitException{

        toUser.printToolCards(client.getClientGame());

        toUser.notify("Selezionare una delle toolcard consentite (Inserire il numero della toolcard)");
        ArrayList<Integer> parameters = new ArrayList<>();

        read = true;


        String val = "";
        int flag = 0;
        int toolCardNumber = 0;
        int index;

        do{

            val = takeInput();

            try{
                toolCardNumber = Integer.parseInt(val);
            }catch(NumberFormatException e){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }
            if(toolCardNumber > 12 || toolCardNumber < 1){
                toUser.error("Stringa non corretta. Selezionare uno dei numeri consentiti");
            }else{
                flag = 1;
            }
            index = client.getClientGame().getToolCardByNumber(toolCardNumber);

        }while(flag == 0 || index == -1);

        toUser.notify("Hai scelto di utilizzare la toolcard "+toolCardNumber+":  "+client.getClientGame().getToolCards().get(index).getName());

        for(String s : client.getClientGame().getToolCards().get(index).getToDo()){



            String x = "";
            int flag2 = 0;
            int i = -1;

            do{

                toUser.request(s);


                x = takeInput();

                try{
                    i = Integer.parseInt(x);
                    flag2 = 1;
                }catch(NumberFormatException e){
                    toUser.error("Inserire un valore numerico");
                }

            }while(flag2 == 0 );
            parameters.add(i);


        }

        read = false;

        client.useToolCard((index+1), parameters);
    }

    @Override
    public void onReserveReceived(ArrayList<Die> diceToPlace) {

    }

    @Override
    public void onDieTool11Received(Die die){

        toUser.notify("Il dado estratto è il seguente : \t"+die.toString2());

        toDo.add("TOOL 11");

    }


    public void die11() throws ExitException, InterruptedException{


        String x = "";
        int flag2 = 0;
        int i = -1;

        read = true;

        do{

            toUser.request("Seleziona il numero che vuoi assegnare a questo dado (da 1 a 6)");

            x = takeInput();

            try{
                i = Integer.parseInt(x);
                flag2 = 1;
            }catch(NumberFormatException e){
                toUser.error("Inserire un valore numerico");
            }

        }while(flag2 == 0 || i<1 || i>6 );

        read = false;

        client.endTool11(i);


    }

    @Override
    public void startErrorDialog(String exceptionName, String errorMessage) throws Exception {



    }


    public void getInput(String input) throws InterruptedException{


        if(read) {
            this.input.clear();
            this.input.add(input);
        }
    }

    private String takeInput() throws InterruptedException, ExitException{

        while(input.size()<1){
            if(!read){
                throw new InterruptedException();
            }
        }
        String s = input.take();
        s.toLowerCase();
        if(s.equals("exit")){
            throw new ExitException();
        }
        return( s);

    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


}
