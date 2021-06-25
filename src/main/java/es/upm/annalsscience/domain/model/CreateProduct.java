package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProduct {
    private String name;
    private String creationDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Long> categoriesId;
    private List<Long> personsId;
    private List<Long> entitiesId;
}
