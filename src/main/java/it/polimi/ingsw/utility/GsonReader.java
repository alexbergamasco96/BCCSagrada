package it.polimi.ingsw.utility;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;

/*
 * Created by FCaimi on 3/05/18
 */

public class GsonReader {

    private Gson gson;

    public GsonReader (){
        gson = new GsonBuilder().serializeNulls().create();

    }

    /**
     * Reads a java object from a JSON file
     * @param path
     * @param c java object class ex. Pattern.class
     * @return the object from JSON file or null (! you must cast the object to use it)
     */

    public Object readObject(String path, Class c){

        try{

            FileReader fileReader = new FileReader(path);

            Object o = gson.fromJson(fileReader,c);

            return o;

        }
        catch(IOException e){
            e.printStackTrace();

            return null;
        }
    }

}
