package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.EntityNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.model.*;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.EntityRepository;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityService {

    private final EntityRepository entityRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;

    @Autowired
    public EntityService(EntityRepository entityRepository,
                         CategoryRepository categoryRepository,
                         PersonRepository personRepository) {
        this.entityRepository = entityRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }

    public Entity create(CreateEntity createEntity) {
        checkIfAllCategoriesExist(createEntity.getCategoriesId());
        checkIfAllPersonsExist(createEntity.getPersonsId());
        return entityRepository.save(createEntity);
    }

    public Optional<Entity> findById(Long id) {
        return entityRepository.findById(id);
    }


    private void checkIfAllCategoriesExist(List<Long> categories) {
        if(!categoryRepository.findAll()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList())
                .containsAll(categories)) {
            throw new CategoryNotExistException("category not exist");
        }
    }

    private void checkIfAllPersonsExist(List<Long> persons) {
        if(!personRepository.findAll()
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList())
                .containsAll(persons)) {
            throw new PersonNotExistException("person not exist");
        }
    }

    public void delete(Long id) {
        Entity entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistException("Entity with id: " + id + " not exist"));
        entityRepository.delete(entity);
    }

    public List<Entity> findAll() {
        return entityRepository.findAll();
    }

    public List<Entity> findByCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotExistException("Category with name " + name + " not exist"));
        return entityRepository.findByCategory(category);
    }

    public Entity update(Long id, CreateEntity updateEntity) {
        Entity entity = entityRepository.findById(id)
                .orElseThrow(() -> new PersonNotExistException("Person with id: " + id + " not exist"));
        checkIfAllCategoriesExist(updateEntity.getCategoriesId());
        entity.setName(updateEntity.getName());
        entity.setCreationDate(updateEntity.getCreationDate());
        entity.setEndDate(updateEntity.getEndDate());
        entity.setDescription(updateEntity.getDescription());
        entity.setImageUrl(updateEntity.getImageUrl());
        entity.setWikiUrl(updateEntity.getWikiUrl());
        entity.setCategories(retrieveCategories(updateEntity.getCategoriesId()));
        entity.setPersons(retrievePersons(updateEntity.getPersonsId()));
        return entityRepository.update(entity);
    }

    private List<Category> retrieveCategories(List<Long> categoriesId) {
        return categoriesId.stream()
                .map(categoryRepository::findById)
                .map(category -> category.orElseThrow(() -> new CategoryNotExistException("Category not exist")))
                .collect(Collectors.toList());
    }

    private List<Person> retrievePersons(List<Long> personsId) {
        return personsId.stream()
                .map(personRepository::findById)
                .map(person -> person.orElseThrow(() -> new PersonNotExistException("Person not exist")))
                .collect(Collectors.toList());
    }
}
