package es.upm.annalsscience.infrastructure.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private RelatedCategoryDTO parent;
    private List<RelatedCategoryDTO> children;

    public CategoryDTO(Long id, String name, RelatedCategoryDTO parent, List<RelatedCategoryDTO> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    @Getter
    @Setter
    public static class RelatedCategoryDTO {
        private Long id;
        private String name;
    }
}
