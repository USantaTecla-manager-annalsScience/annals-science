package es.upm.annalsscience.infrastructure.persistence.jpa;


import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findByParentId(Long parentId);

}
