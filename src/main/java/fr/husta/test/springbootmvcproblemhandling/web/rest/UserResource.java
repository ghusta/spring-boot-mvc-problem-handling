package fr.husta.test.springbootmvcproblemhandling.web.rest;

import fr.husta.test.springbootmvcproblemhandling.domain.User;
import fr.husta.test.springbootmvcproblemhandling.error.CustomValidationException;
import fr.husta.test.springbootmvcproblemhandling.error.TeapotException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserResource {

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        log.debug("START getUserById()");

        if (id == 404) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ID %d non trouvé", id));
        }
        if (id == 418) {
            throw new TeapotException(HttpStatus.I_AM_A_TEAPOT.getReasonPhrase());
        }
        if (id == 999) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("ID %d invalide", id));
        }
        if (id == 9999) {
            throw new CustomValidationException(String.format("ID %d invalide", id));
        }
        if (id == 500) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id == 501) {
            throw new UnsupportedOperationException("Not yet implemented !");
        }

        User fakeUser = new User();
        fakeUser.setId(99L);
        fakeUser.setLastName("DOE");
        fakeUser.setFirstName("John");

        return ResponseEntity.ok(fakeUser);
    }

}
