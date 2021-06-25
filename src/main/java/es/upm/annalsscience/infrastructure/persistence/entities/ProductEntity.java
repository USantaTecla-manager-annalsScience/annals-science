package es.upm.annalsscience.infrastructure.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String creationDate;

    @Column
    private String endDate;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private String wikiUrl;

    @ManyToMany(targetEntity = CategoryEntity.class)
    private List<CategoryEntity> categories;

    @ManyToMany(targetEntity = PersonEntity.class)
    private List<PersonEntity> persons;

    @ManyToMany(targetEntity = EntityEntity.class)
    private List<EntityEntity> entities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(creationDate, that.creationDate) && Objects.equals(endDate, that.endDate) && Objects.equals(description, that.description) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(wikiUrl, that.wikiUrl) && Objects.equals(categories, that.categories) && Objects.equals(persons, that.persons) && Objects.equals(entities, that.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, endDate, description, imageUrl, wikiUrl, categories, persons, entities);
    }
}
