package es.upm.annalsscience.infrastructure.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    private String name;
    private String surname;
    private String password;
    private String email;
}
