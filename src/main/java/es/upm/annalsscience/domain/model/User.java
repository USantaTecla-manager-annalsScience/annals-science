package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
}
