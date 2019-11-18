package fr.husta.test.springbootmvcproblemhandling.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

import java.time.Instant;

/**
 * See : https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-controller-advice
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity handleCustomValidationException(CustomValidationException ex /*, WebRequest request */) {
        // ResponseEntity responseEntity = handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        ThrowableProblem problem = Problem.builder()
                .withDetail(ex.getMessage())
                .withStatus(Status.BAD_REQUEST)
                .withTitle(Status.BAD_REQUEST.getReasonPhrase())
                .with("timestamp", Instant.now())
                .build();
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .header("X-Rfc-Implementation", "RFC 7807")
                .body(problem);
        log.debug("RESP Content-Type = {}", responseEntity.getHeaders().getContentType());
        return responseEntity;
    }

    @ExceptionHandler(TeapotException.class)
    public ResponseEntity handleTeapotException(TeapotException ex) {
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .header("X-Implements-RFC", "7807") // custom header
                .body(buildProblem(ex, HttpStatus.NOT_IMPLEMENTED));
    }

    private ThrowableProblem buildProblem(Exception ex) {
        return Problem.builder()
                .withDetail(ex.getMessage())
                .withTitle(ex.getClass().getSimpleName())
                .build();
    }

    private ThrowableProblem buildProblem(Exception ex, HttpStatus httpStatus) {
        return Problem.builder()
                .withDetail(ex.getMessage())
                .withTitle(ex.getClass().getSimpleName())
                .withStatus(new HttpStatusAdapter(httpStatus))
                .build();
    }

}
