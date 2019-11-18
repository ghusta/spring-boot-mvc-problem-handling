package fr.husta.test.springbootmvcproblemhandling;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@Slf4j
class SpringBootMvcProblemHandlingApplicationTests {

	@Autowired
	private Environment environment;

    @Autowired(required = false)
    private DefaultErrorWebExceptionHandler defaultErrorWebExceptionHandler;

    @Test
    void contextLoads() {
        log.debug("ENTER contextLoads()");
        // empty test
    }

}
