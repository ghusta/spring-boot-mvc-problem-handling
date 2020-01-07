package fr.husta.test.springbootmvcproblemhandling;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.problem.ProblemModule;

@SpringBootApplication
public class SpringBootMvcProblemHandlingApplication {

//    @Bean
//    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.modulesToInstall(new ProblemModule().withStackTraces(false));
//        return builder;
//    }

    /**
     * See : https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-customize-the-jackson-objectmapper
     */
    @Bean
    public Module problemModule() {
        return new ProblemModule().withStackTraces(false);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcProblemHandlingApplication.class, args);
    }

}
