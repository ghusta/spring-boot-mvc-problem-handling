package fr.husta.test.springbootmvcproblemhandling.web.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * See : https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.1.2.RELEASE/#_spring_cloud_contract_wiremock
 */
@WebFluxTest(controllers = UserResource.class)
@RunWith(SpringRunner.class)
@Slf4j
public class UserResourceMockServerTest {

    //    @Autowired
    private WebTestClient webClient;

    private WireMockServer wireMockServer;

    @Before
    public void setUp() throws Exception {
        WireMockConfiguration options = WireMockConfiguration.options()
                .dynamicPort()
                .bindAddress(Options.DEFAULT_BIND_ADDRESS);
        wireMockServer = new WireMockServer(options);

        // See also :  com.github.tomakehurst.wiremock.junit.WireMockRule
        wireMockServer.start();

        webClient = WebTestClient
                .bindToServer()
                .baseUrl(wireMockServer.baseUrl())
                .filter(logRequest())
                .build();
    }

    /**
     * See : https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/#2-logging-all-the-requests-using-a-filter-function
     */
    private ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            HttpHeaders headers = request.headers();
            log.info("Request : {} - {}", request.method(), request.url());
            return next.exchange(request);
        };
    }

    @After
    public void tearDown() throws Exception {
        wireMockServer.stop();
    }

    @Test
    public void testGetUserById() {
        String jsonResponse = "{ \"id\": 123, \"lastName\": \"Test\" }";
        wireMockServer.stubFor(get(urlEqualTo("/api/users/123"))
                .willReturn(aResponse()
                        .withBody(jsonResponse)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value()))
        );

        webClient
                .get()
                .uri("/api/users/{id}", 123)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(123)
                .jsonPath("$.lastName").isEqualTo("Test");
    }

    @Test
    public void testUrlNotMatching() {
        wireMockServer.stubFor(get(urlEqualTo("/api/usersssss/123"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value()))
        );

        webClient
                .get()
                .uri("/api/usersssss/{id}", 123)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    public void testGetUserById_badRequest() {
        String jsonResponse = "{\"error\" : \"Invalid request\"}";
        wireMockServer.stubFor(get(urlEqualTo("/api/users/999"))
                .willReturn(aResponse()
                        .withBody(jsonResponse)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withStatus(HttpStatus.BAD_REQUEST.value()))
        );

        webClient
                .get()
                .uri("/api/users/{id}", 999)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Invalid request");
    }

}