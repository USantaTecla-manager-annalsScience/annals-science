package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateCategory {
    private String name;
    private Long parentId;

    public CreateCategory(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
