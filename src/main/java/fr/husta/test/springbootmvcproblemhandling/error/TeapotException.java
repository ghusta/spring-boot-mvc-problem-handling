package fr.husta.test.springbootmvcproblemhandling.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Will be handled by {@link fr.husta.test.springbootmvcproblemhandling.problem.advice.TeapotExceptionAdviceTrait TeapotExceptionAdviceTrait}
 * (an {@link org.springframework.web.bind.annotation.ExceptionHandler ExceptionHandler}).
 * <p>
 * No need for {@link ResponseStatus @ResponseStatus}.
 * </p>
 */
// @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class TeapotException extends RuntimeException {

    public TeapotException(String message) {
        super(message);
    }

    public TeapotException(String message, Throwable cause) {
        super(message, cause);
    }
}
