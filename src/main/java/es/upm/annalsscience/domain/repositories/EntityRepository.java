package es.upm.annalsscience.domain.repositories;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateEntity;
import es.upm.annalsscience.domain.model.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityRepository {
    Entity save(CreateEntity createEntity);
    Optional<Entity> findById(Long id);
    void delete(Entity entity);
    List<Entity> findAll();
    List<Entity> findByCategory(Category category);
    Entity update(Entity entity);
}
