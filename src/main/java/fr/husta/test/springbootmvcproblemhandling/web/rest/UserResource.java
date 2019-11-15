package fr.husta.test.springbootmvcproblemhandling.web.rest;

import fr.husta.test.springbootmvcproblemhandling.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserResource {

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User fakeUser = new User();
        fakeUser.setId(99L);
        fakeUser.setLastName("DOE");
        fakeUser.setFirstName("John");

        return ResponseEntity.ok(fakeUser);
    }

}
