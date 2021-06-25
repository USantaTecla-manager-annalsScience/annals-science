package es.upm.annalsscience.infrastructure.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @Column
    private String surname;

}
