package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.EntityDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.PersonDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.ProductDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonRepositoryAdapter implements PersonRepository {

    private final PersonDAO personDAO;
    private final EntityDAO entityDAO;
    private final ProductDAO productDAO;
    private final PersonMapper personMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public PersonRepositoryAdapter(PersonDAO personDAO,
                                   EntityDAO entityDAO,
                                   ProductDAO productDAO,
                                   PersonMapper personMapper,
                                   CategoryMapper categoryMapper) {
        this.personDAO = personDAO;
        this.entityDAO = entityDAO;
        this.productDAO = productDAO;
        this.personMapper = personMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return this.personDAO.findById(id)
                .map(personMapper::map);
    }

    @Override
    public Person save(CreatePerson createPerson) {
        PersonEntity personEntity = personMapper.map(createPerson);
        return personMapper.map(this.personDAO.save(personEntity));
    }

    @Override
    public List<Person> findAll() {
        return personMapper.map(this.personDAO.findAll());
    }

    @Override
    public List<Person> findByCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.map(category);
        return personMapper.map(this.personDAO.findByCategories(categoryEntity));
    }

    @Override
    public void delete(Person person) {
        PersonEntity personEntity = personMapper.map(person);

        List<EntityEntity> entitiesWithPersonsToDelete = entityDAO.findAll()
                .stream()
                .peek(entityEntity -> entityEntity.getPersons().remove(personEntity))
                .collect(Collectors.toList());
        entitiesWithPersonsToDelete.forEach(entityDAO::save);

        List<ProductEntity> productsWithPersonsToDelete = productDAO.findAll()
                .stream()
                .peek(entityEntity -> entityEntity.getPersons().remove(personEntity))
                .collect(Collectors.toList());
        productsWithPersonsToDelete.forEach(productDAO::save);

        this.personDAO.delete(personEntity);
    }

    @Override
    public Person update(Person person) {
        PersonEntity personEntity = personMapper.map(person);
        return personMapper.map(personDAO.save(personEntity));
    }
}
