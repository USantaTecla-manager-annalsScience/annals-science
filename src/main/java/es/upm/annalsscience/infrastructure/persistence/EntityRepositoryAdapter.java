package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateEntity;
import es.upm.annalsscience.domain.model.Entity;
import es.upm.annalsscience.domain.repositories.EntityRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.EntityDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EntityRepositoryAdapter implements EntityRepository {

    private final EntityDAO entityDAO;
    private final EntityMapper entityMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public EntityRepositoryAdapter(EntityDAO entityDAO,
                                   EntityMapper entityMapper,
                                   CategoryMapper categoryMapper) {
        this.entityDAO = entityDAO;
        this.entityMapper = entityMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Entity save(CreateEntity createEntity) {
        EntityEntity entityEntity = entityMapper.map(createEntity);
        return entityMapper.map(this.entityDAO.save(entityEntity));
    }

    @Override
    public Optional<Entity> findById(Long id) {
        return this.entityDAO.findById(id)
                .map(entityMapper::map);
    }

    @Override
    public void delete(Entity entity) {
        this.entityDAO.delete(entityMapper.map(entity));
    }

    @Override
    public List<Entity> findAll() {
        return entityMapper.map(entityDAO.findAll());
    }

    @Override
    public List<Entity> findByCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.map(category);
        return entityMapper.map(entityDAO.findByCategories(categoryEntity));
    }

    @Override
    public Entity update(Entity entity) {
        EntityEntity entityEntity = entityMapper.map(entity);
        return entityMapper.map(entityDAO.save(entityEntity));
    }
}
