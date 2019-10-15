package it.polimi.ingsw.utility;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

/*
 * Created by FCaimi on 3/05/18
 */

public class GsonWriter {

    private Gson gson;

    public GsonWriter (){
        gson = new GsonBuilder().serializeNulls().create();
    }

    /**
     * Writes a java object into a JSON file (if it doesn't find the file it creates a new one with the given name)
     * @param path example "src/main/resources/fileName.json"
     * @param obj the object to be written
     */

    public void writeObject(String path, Object obj){


        try{

            FileWriter fileWriter = new FileWriter(path);

            String json = gson.toJson(obj);

            //System.out.println(json);

            fileWriter.write(json);
            fileWriter.flush();

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}