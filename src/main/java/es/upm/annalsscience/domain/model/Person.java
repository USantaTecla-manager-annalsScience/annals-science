package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Person {
    private Long id;
    private String name;
    private String surname;
    private String birthDate;
    private String deathDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Category> categories;
}
