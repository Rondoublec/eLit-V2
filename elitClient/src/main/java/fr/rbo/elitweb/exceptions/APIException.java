package fr.rbo.elitweb.exceptions;

public class APIException extends RuntimeException{

    public APIException(String service, String message, String trace) {
        super("Erreur de communication avec le service : " + service + " Message :" + message + " StackTrace : " + trace);
    }

}
