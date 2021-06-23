package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateEntity;
import es.upm.annalsscience.domain.model.Entity;
import es.upm.annalsscience.domain.repositories.EntityRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.EntityDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.ProductDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.EntityMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EntityRepositoryAdapter implements EntityRepository {

    private final EntityDAO entityDAO;
    private final ProductDAO productDAO;
    private final EntityMapper entityMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public EntityRepositoryAdapter(EntityDAO entityDAO,
                                   ProductDAO productDAO,
                                   EntityMapper entityMapper,
                                   CategoryMapper categoryMapper,
                                   ProductMapper productMapper) {
        this.entityDAO = entityDAO;
        this.productDAO = productDAO;
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
        EntityEntity entityEntity = entityMapper.map(entity);
        List<ProductEntity> productsWithEntitiesToDelete = productDAO.findAll()
                .stream()
                .peek(productEntity -> productEntity.getEntities().remove(entityEntity))
                .collect(Collectors.toList());
        productsWithEntitiesToDelete.forEach(productDAO::save);

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
