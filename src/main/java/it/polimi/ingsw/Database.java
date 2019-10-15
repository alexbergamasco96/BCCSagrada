package it.polimi.ingsw;

import it.polimi.ingsw.exception.AlreadyLoggedException;
import it.polimi.ingsw.exception.NoUserException;
import it.polimi.ingsw.exception.AlreadyUsedUsernameException;
import it.polimi.ingsw.exception.WrongPasswordException;
import it.polimi.ingsw.utility.BestScore;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by FCaimi on 14/05/18
 */

public class Database {

    private static Database database;

    private Connection conn;

    private static final String URL = "src/main/resources/db.sqlite";

    private Database(){

        try{

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+URL);
        }
        catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * singleton method
     * @return singleton instance
     */

    public static Database getDb(){
        if(database == null){
            database = new Database();
        }
        return database;
    }

    /**
     * executes sql insert statement
     * @param s sql query
     */

    public void executeInsert(String s){
        try {
            Statement stmt = conn.createStatement();

            String sql = s;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves new player into db
     * @param username player username
     * @param password player password
     * @throws AlreadyUsedUsernameException
     */


    public void signIn(String username, String password) throws AlreadyUsedUsernameException {

        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE USERNAME = '"+username+"'";

            ResultSet result = stmt.executeQuery(query);

            if(!result.next()) {

                String query2 = "INSERT INTO USERS VALUES ('DEFAULT', '" + username + "', '" + password + "', 0, 0)";

                stmt.executeUpdate(query2);

            }
            else{
                throw new AlreadyUsedUsernameException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * check if the player exists into db
     * @param username player username
     * @param password player password
     * @return result of query
     * @throws NoUserException
     * @throws WrongPasswordException
     * @throws AlreadyLoggedException
     */


    public Boolean login(String username, String password) throws NoUserException, WrongPasswordException, AlreadyLoggedException {

        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE USERNAME = '"+username+"'";

            ResultSet result = stmt.executeQuery(query);


            while (result.next()){
                String user = result.getString("USERNAME");
                String pass = result.getString("PASSWORD");
                if(result.getInt("LOGGED")==0) {
                    if (user.equals(username)) {
                        if (pass.equals(password)) {
                            String sql = "UPDATE USERS SET LOGGED = 1 WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
                            stmt.executeUpdate(sql);
                            return true;
                        } else {
                            throw new WrongPasswordException();
                        }
                    } else {
                        throw new NoUserException();
                    }
                } else {
                    throw new AlreadyLoggedException();
                }

            }

            throw new NoUserException();

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * get db id of player
     * @param username player username
     * @param password player password
     * @return player id
     */

    public int getID(String username, String password){

        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"'";

            ResultSet result = stmt.executeQuery(query);


            while (result.next()){
                //String user = result.getString("USERNAME");
                //String pass = result.getString("PASSWORD");
                //if(pass.equals(password) && user.equals(username)){
                    return result.getInt("ID");
                //}
            }

            return -1;

        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * gets player bestscore
     * @param username player username
     * @return player bestscore
     */

    public int getBestScore(String username){
        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE USERNAME = '"+username+"'";

            ResultSet result = stmt.executeQuery(query);


            while (result.next()){
                //String user = result.getString("USERNAME");
                //String pass = result.getString("PASSWORD");
                //if(pass.equals(password) && user.equals(username)){
                    return result.getInt("BEST_SCORE");
                //}
            }

            return -1;

        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * checks if a player is logged into the game
     * @param username player username
     * @param password player password
     * @return result of check
     * @throws NoUserException
     * @throws WrongPasswordException
     */

    public boolean isLogged(String username, String password) throws NoUserException, WrongPasswordException{
        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE USERNAME = '"+username+"'";

            ResultSet result = stmt.executeQuery(query);


            while (result.next()){
                String user = result.getString("USERNAME");
                String pass = result.getString("PASSWORD");
                int logged = result.getInt("LOGGED");
                if (user.equals(username)) {
                    if(pass.equals(password)) {
                        if (logged == 0) {
                            return false;
                        }
                        else {
                            return true;
                        }
                    }
                    else{
                        throw new WrongPasswordException();
                    }
                }
                else{
                    throw new NoUserException();
                }
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * sets new bestscore of a player
     * @param username player username
     * @param bestScore player new bestscore
     */

    public void setBestScore(String username, int bestScore){

        try {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE USERS SET BEST_SCORE = "+bestScore+" WHERE USERNAME = '"+username+"'";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * logs out the player
     * @param username player username
     */

    public void logout(String username){
        try {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE USERS SET LOGGED = 0 WHERE USERNAME = '"+username+"'";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets all player not logged
     */

    public void reset(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE USERS SET LOGGED = 0";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets ten all time best scores
     * @return ten all time best scores
     */

    public ArrayList<BestScore> getBestTen(){
        ArrayList<BestScore> bestScores = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT DISTINCT * FROM USERS ORDER BY BEST_SCORE DESC LIMIT 10";

            ResultSet result = stmt.executeQuery(query);

            while (result.next()){
               bestScores.add(new BestScore(result.getString("USERNAME"),result.getInt("BEST_SCORE")));
            }

            return bestScores;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
