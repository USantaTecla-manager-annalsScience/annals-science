package es.upm.annalsscience.infrastructure.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String birthDate;

    @Column
    private String deathDate;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private String wikiUrl;

    @ManyToMany(targetEntity = CategoryEntity.class)
    private List<CategoryEntity> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(birthDate, that.birthDate) && Objects.equals(deathDate, that.deathDate) && Objects.equals(description, that.description) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(wikiUrl, that.wikiUrl) && Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDate, deathDate, description, imageUrl, wikiUrl, categories);
    }
}
