package fr.husta.test.springbootmvcproblemhandling.web.rest;

import fr.husta.test.springbootmvcproblemhandling.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class UserResourceTest {

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setLastName("TEST");
        user.setFirstName("Toto");
    }

    @Test
    void testGetUserById_noMock() {
        webClient
                .get()
                .uri("/api/users/{id}", 123)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.lastName").isEqualTo("Test");
    }

}