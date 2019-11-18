package fr.husta.test.springbootmvcproblemhandling.web.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = UserResource.class)
@ExtendWith(SpringExtension.class)
class UserResourceTest {

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetUserById_noMock() {
        webClient
                .get()
                .uri("/api/users/{id}", 123)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").value(Matchers.not(Matchers.equalTo(1)))
                .jsonPath("$.lastName").isEqualTo("DOE");
    }

    @Test
    void testGetUserById_exception404() {
        webClient
                .get()
                .uri("/api/users/{id}", 404)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetUserById_exception418() {
        webClient
                .get()
                .uri("/api/users/{id}", 418)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.I_AM_A_TEAPOT);
    }

    @Test
    void testGetUserById_exception999() {
        webClient
                .get()
                .uri("/api/users/{id}", 999)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetUserById_exception9999() {
        webClient
                .get()
                .uri("/api/users/{id}", 9999)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    }

    @Test
    void testUrlNotMatching() {
        webClient
                .get()
                .uri("/api/usersssss/{id}", 123)
                .exchange()
                .expectStatus().isNotFound();
    }

}