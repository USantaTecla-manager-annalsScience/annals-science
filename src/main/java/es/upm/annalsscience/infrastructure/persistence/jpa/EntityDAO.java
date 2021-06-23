package es.upm.annalsscience.infrastructure.persistence.jpa;

import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityDAO extends JpaRepository<EntityEntity, Long> {
    List<EntityEntity> findByCategories(CategoryEntity categoryEntity);
}
