package it.polimi.ingsw.netObject.response;

public class ExceptionResponseObject extends ResponseNetObject {

    private String exceptionName;

    public ExceptionResponseObject(String header, String response, String exceptionName) {
        super(header, response);
        this.exceptionName = exceptionName;
    }

    public String getExceptionName() {
        return exceptionName;
    }
}
