package fr.husta.test.springbootmvcproblemhandling.problem.advice;

import fr.husta.test.springbootmvcproblemhandling.error.TeapotException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

/**
 * @see fr.husta.test.springbootmvcproblemhandling.error.TeapotException
 */
public interface TeapotExceptionAdviceTrait extends AdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleUnsupportedOperation(
            final TeapotException exception,
            final NativeWebRequest request) {
        return create(Status.I_AM_A_TEAPOT, exception, request);
    }

}
