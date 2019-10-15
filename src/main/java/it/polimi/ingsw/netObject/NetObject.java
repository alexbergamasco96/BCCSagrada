package it.polimi.ingsw.netObject;



import java.io.Serializable;

public abstract class NetObject implements Serializable, Cloneable{

    String header;

    public NetObject(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

}
