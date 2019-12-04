package fr.husta.test.springbootmvcproblemhandling.web.reactive.function.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

/**
 * Similar to {@link org.springframework.web.reactive.function.client.ExchangeFilterFunctions}.
 *
 * @see org.springframework.web.reactive.function.client.ExchangeFilterFunctions
 */
@Slf4j
public abstract class MyExchangeFilterFunctions {

    /**
     * See : https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/#2-logging-all-the-requests-using-a-filter-function
     */
    public static ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            HttpHeaders headers = request.headers();
            log.info("Request : {} - {}", request.method(), request.url());
            return next.exchange(request);
        };
    }

}
