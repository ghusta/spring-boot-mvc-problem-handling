package fr.husta.test.springbootmvcproblemhandling.error.handler;

import fr.husta.test.springbootmvcproblemhandling.problem.advice.TeapotExceptionAdviceTrait;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandling implements ProblemHandling, TeapotExceptionAdviceTrait {

    /**
     * Post process
     */
    @Override
    public ResponseEntity<Problem> process(ResponseEntity<Problem> entity) {
        return ResponseEntity.status(entity.getStatusCode())
                .headers(entity.getHeaders())
                .header("Custom-Process", "Post-processed !")
                .body(entity.getBody());
    }

}
