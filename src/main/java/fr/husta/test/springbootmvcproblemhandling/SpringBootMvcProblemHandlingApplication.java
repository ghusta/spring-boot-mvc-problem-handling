package fr.husta.test.springbootmvcproblemhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.problem.ProblemModule;

@SpringBootApplication
public class SpringBootMvcProblemHandlingApplication {

    @Bean
    public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.registerModule(new ProblemModule().withStackTraces(false));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcProblemHandlingApplication.class, args);
    }

}
