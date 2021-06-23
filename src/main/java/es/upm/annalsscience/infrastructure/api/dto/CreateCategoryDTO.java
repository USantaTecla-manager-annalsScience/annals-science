package es.upm.annalsscience.infrastructure.api.dto;

public class CreateCategoryDTO {
    private String name;
    private Long parentId;

    public CreateCategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }
}
