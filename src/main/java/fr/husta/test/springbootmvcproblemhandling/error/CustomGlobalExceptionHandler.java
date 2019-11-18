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
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

/**
 * See : https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-controller-advice
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity handleCustomValidationException(CustomValidationException ex /*, WebRequest request */) {
        // ResponseEntity responseEntity = handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(ex.getMessage());
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
