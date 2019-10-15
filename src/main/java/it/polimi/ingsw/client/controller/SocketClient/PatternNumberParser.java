package it.polimi.ingsw.client.controller.SocketClient;

import it.polimi.ingsw.server.model.Pattern;
import it.polimi.ingsw.utility.GsonReader;

import java.util.ArrayList;

public class PatternNumberParser {

    /**
     * translates four patternNumbers received in setUp into pattern Object
     * @param p1 pattern number
     * @param p2
     * @param p3
     * @param p4
     * @return array of pattern objects
     */

    public ArrayList<Pattern> parse(int p1, int p2, int p3, int p4){

        ArrayList<Pattern> patterns = new ArrayList<>();

        GsonReader gsonReader = new GsonReader();

        patterns.add((Pattern)gsonReader.readObject("src/main/resources/json/pattern/"+p1+".json", Pattern.class));
        patterns.add((Pattern)gsonReader.readObject("src/main/resources/json/pattern/"+p2+".json", Pattern.class));
        patterns.add((Pattern)gsonReader.readObject("src/main/resources/json/pattern/"+p3+".json", Pattern.class));
        patterns.add((Pattern)gsonReader.readObject("src/main/resources/json/pattern/"+p4+".json", Pattern.class));


        return patterns;


    }

    /**
     * translates sigle patternNumber received in setUp into pattern Object
     * @param p pattern number
     * @return pattern object
     */

    public Pattern parseSingle(int p){

        Pattern pattern = new Pattern();

        GsonReader gsonReader = new GsonReader();

        pattern = (Pattern)gsonReader.readObject("src/main/resources/json/pattern/"+p+".json", Pattern.class);

        return pattern;

    }


}
