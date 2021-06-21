package es.upm.annalsscience.infrastructure.persistence.jpa;

import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategories(CategoryEntity categoryEntity);
}
