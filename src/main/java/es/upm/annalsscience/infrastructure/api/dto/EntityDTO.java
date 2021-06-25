package es.upm.annalsscience.infrastructure.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityDTO {
    private Long id;
    private String name;
    private String creationDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<CategoryDTO> categories;
    private List<PersonDTO> persons;
}
