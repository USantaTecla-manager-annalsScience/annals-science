package es.upm.annalsscience.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Category {
    private Long id;
    private String name;
    private RelatedCategory parent;
    private List<RelatedCategory> children = Collections.emptyList();

    public Category(Long id, String name, RelatedCategory parent, List<RelatedCategory> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    public Category() {

    }

    @Getter
    @Setter
    public static class RelatedCategory {
        private Long id;
        private String name;
    }

}
