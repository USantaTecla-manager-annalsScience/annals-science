package es.upm.annalsscience.infrastructure.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    private String name;
    private Long parentId;

    public CreateCategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
