package fr.husta.test.springbootmvcproblemhandling.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

}
