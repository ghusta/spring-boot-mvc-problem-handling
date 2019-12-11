package fr.husta.test.springbootmvcproblemhandling.error.handler;

import fr.husta.test.springbootmvcproblemhandling.problem.advice.TeapotExceptionAdviceTrait;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandling implements ProblemHandling, TeapotExceptionAdviceTrait {

}
