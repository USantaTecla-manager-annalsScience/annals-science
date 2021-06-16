package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.PersonDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonRepositoryAdapter implements PersonRepository {

    private final PersonDAO personDAO;
    private final PersonMapper personMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public PersonRepositoryAdapter(PersonDAO personDAO,
                                   PersonMapper personMapper,
                                   CategoryMapper categoryMapper) {
        this.personDAO = personDAO;
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
        this.personDAO.delete(personMapper.map(person));
    }

    @Override
    public Person update(Person person) {
        PersonEntity personEntity = personMapper.map(person);
        return personMapper.map(personDAO.save(personEntity));
    }
}
