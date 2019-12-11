package fr.husta.test.springbootmvcproblemhandling.problem.advice;

import fr.husta.test.springbootmvcproblemhandling.error.TeapotException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

/**
 * @see fr.husta.test.springbootmvcproblemhandling.error.TeapotException
 */
public interface TeapotExceptionAdviceTrait extends AdviceTrait {

    @ExceptionHandler(TeapotException.class)
    default ResponseEntity<Problem> handleTeapotException(
            final TeapotException exception,
            final NativeWebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Rfc-Implementation", "RFC 7807");
        return create(Status.I_AM_A_TEAPOT, exception, request, httpHeaders);
    }

}
