package cz.idomatojde.exceptions;

/**
 * Exception raised when the supplied phone number format is not recognized
 *
 * @author Michal Hazdra
 */
public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {
    }

    public InvalidPhoneNumberException(String message) {
        super(message);
    }

    public InvalidPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
