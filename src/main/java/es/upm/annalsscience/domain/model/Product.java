package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String creationDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Category> categories;
    private List<Person> persons;
    private List<Entity> entities;
}
