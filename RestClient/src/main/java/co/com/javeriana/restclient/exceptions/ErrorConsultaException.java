package co.com.javeriana.restclient.exceptions;

/**
 * @author Concesión RUNT
 */
public class ErrorConsultaException extends Exception {

    public ErrorConsultaException(String message) {
        super(message);
    }

    public ErrorConsultaException(String message, Throwable cause) {
        super(message, cause);
    }

}
