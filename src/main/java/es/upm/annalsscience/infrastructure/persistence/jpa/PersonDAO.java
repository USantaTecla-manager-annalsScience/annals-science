package es.upm.annalsscience.infrastructure.persistence.jpa;

import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDAO extends JpaRepository<PersonEntity, Long> {
    List<PersonEntity> findByCategories(CategoryEntity categoryEntity);
}
