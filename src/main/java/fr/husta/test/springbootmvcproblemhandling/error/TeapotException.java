package fr.husta.test.springbootmvcproblemhandling.error;

public class TeapotException extends RuntimeException {

    public TeapotException(String message) {
        super(message);
    }

    public TeapotException(String message, Throwable cause) {
        super(message, cause);
    }
}
