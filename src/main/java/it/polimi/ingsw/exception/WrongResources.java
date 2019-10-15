package it.polimi.ingsw.exception;

/*
 * Created by FCaimi on 10/05/18
 */

public class WrongResources extends Exception{

    private WrongResourcesType wrongResourcesType;

    public WrongResources(String s){
        super(s);
    }

    public WrongResources(WrongResourcesType wrt){
        super();
        this.wrongResourcesType = wrt;
    }

    public WrongResources(WrongResourcesType wrt, String s){
        super(s);
        this.wrongResourcesType = wrt;
    }

}
