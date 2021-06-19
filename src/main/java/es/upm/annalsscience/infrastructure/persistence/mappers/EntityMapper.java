package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.CreateEntity;
import es.upm.annalsscience.domain.model.Entity;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;
    private final PersonDAO personDAO;
    private final PersonMapper personMapper;

    @Autowired
    public EntityMapper(CategoryDAO categoryDAO, CategoryMapper categoryMapper, PersonDAO personDAO, PersonMapper personMapper) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
        this.personDAO = personDAO;
        this.personMapper = personMapper;
    }

    public EntityEntity map(CreateEntity createEntity) {
        EntityEntity entityEntity = new EntityEntity();
        entityEntity.setName(createEntity.getName());
        entityEntity.setCreationDate(createEntity.getCreationDate());
        entityEntity.setEndDate(createEntity.getEndDate());
        entityEntity.setDescription(createEntity.getDescription());
        entityEntity.setImageUrl(createEntity.getImageUrl());
        entityEntity.setWikiUrl(createEntity.getWikiUrl());
        entityEntity.setCategories(retrieveCategories(createEntity.getCategoriesId()));
        entityEntity.setPersons(retrievePersons(createEntity.getPersonsId()));
        return entityEntity;
    }

    private List<CategoryEntity> retrieveCategories(List<Long> categoriesId) {
        return categoriesId
                .stream()
                .map(id -> this.categoryDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    private List<PersonEntity> retrievePersons(List<Long> categoriesId) {
        return categoriesId
                .stream()
                .map(id -> this.personDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    public Entity map(EntityEntity entityEntity) {
        Entity entity = new Entity();
        entity.setId(entityEntity.getId());
        entity.setName(entityEntity.getName());
        entity.setCreationDate(entityEntity.getCreationDate());
        entity.setEndDate(entityEntity.getEndDate());
        entity.setDescription(entityEntity.getDescription());
        entity.setImageUrl(entityEntity.getImageUrl());
        entity.setWikiUrl(entityEntity.getWikiUrl());
        entity.setCategories(categoryMapper.mapToDomain(entityEntity.getCategories()));
        entity.setPersons(personMapper.map(entityEntity.getPersons()));
        return entity;
    }

    public EntityEntity map(Entity entity) {
        EntityEntity entityEntity = new EntityEntity();
        entityEntity.setId(entity.getId());
        entityEntity.setName(entity.getName());
        entityEntity.setCreationDate(entity.getCreationDate());
        entityEntity.setEndDate(entity.getEndDate());
        entityEntity.setDescription(entity.getDescription());
        entityEntity.setImageUrl(entity.getImageUrl());
        entityEntity.setWikiUrl(entity.getWikiUrl());
        entityEntity.setCategories(categoryMapper.map(entity.getCategories()));
        entityEntity.setPersons(personMapper.mapToEntities(entity.getPersons()));
        return entityEntity;
    }

    public List<Entity> map(List<EntityEntity> entityList) {
        return entityList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<EntityEntity> mapToEntities(List<Entity> entities) {
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
