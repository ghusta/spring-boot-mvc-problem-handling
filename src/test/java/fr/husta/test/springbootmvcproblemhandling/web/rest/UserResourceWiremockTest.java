package fr.husta.test.springbootmvcproblemhandling.web.rest;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * See : https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.1.2.RELEASE/#_spring_cloud_contract_wiremock
 */
@WebFluxTest
// @ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8080)
public class UserResourceWiremockTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testGetUserById() {
        String jsonResponse = "{ \"id\": 123, \"lastName\": \"Test\" }";
        WireMock.stubFor(get(urlEqualTo("/api/users/123"))
                .willReturn(aResponse()
                        .withBody(jsonResponse)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value()))
        );

        webClient
                .get()
                .uri("/api/users/{id}", 123)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(123)
                .jsonPath("$.lastName").isEqualTo("Test");
    }

    @Test
    public void testUrlNotMatching() {
        WireMock.stubFor(get(urlEqualTo("/api/users/123"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value()))
        );

        webClient
                .get()
                .uri("/api/usersssss/{id}", 123)
                .exchange()
                .expectStatus().isNotFound();
    }

}