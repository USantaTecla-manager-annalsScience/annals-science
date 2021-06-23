package es.upm.annalsscience.domain.model;

public class CreateCategory {
    private String name;
    private Long parentId;

    public CreateCategory(String name, Long parentId) {
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
