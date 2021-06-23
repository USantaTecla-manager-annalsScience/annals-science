package es.upm.annalsscience.infrastructure.api.dto;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelatedCategoryDTO getParent() {
        return parent;
    }

    public void setParent(RelatedCategoryDTO parent) {
        this.parent = parent;
    }

    public List<RelatedCategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RelatedCategoryDTO> children) {
        this.children = children;
    }


    public static class RelatedCategoryDTO {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
