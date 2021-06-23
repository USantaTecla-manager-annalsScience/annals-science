package es.upm.annalsscience.domain.model;

import java.util.Collections;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelatedCategory getParent() {
        return parent;
    }

    public void setParent(RelatedCategory parent) {
        this.parent = parent;
    }

    public List<RelatedCategory> getChildren() {
        return children;
    }

    public void setChildren(List<RelatedCategory> children) {
        this.children = children;
    }


    public static class RelatedCategory {
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
