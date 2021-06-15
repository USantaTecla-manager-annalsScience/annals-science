package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;

    public PersonMapper(CategoryDAO categoryDAO, CategoryMapper categoryMapper) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
    }

    public PersonEntity map(CreatePerson createPerson) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(createPerson.getName());
        personEntity.setSurname(createPerson.getSurname());
        personEntity.setBirthDate(createPerson.getBirthDate());
        personEntity.setDeathDate(createPerson.getDeathDate());
        personEntity.setDescription(createPerson.getDescription());
        personEntity.setImageUrl(createPerson.getImageUrl());
        personEntity.setWikiUrl(createPerson.getWikiUrl());
        personEntity.setCategories(retrieveCategories(createPerson.getCategoriesId()));
        return personEntity;
    }

    private List<CategoryEntity> retrieveCategories(List<Long> categoriesId) {
        return categoriesId
                .stream()
                .map(id -> this.categoryDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    public Person map(PersonEntity personEntity) {
        Person person = new Person();
        person.setId(personEntity.getId());
        person.setName(personEntity.getName());
        person.setSurname(personEntity.getSurname());
        person.setBirthDate(personEntity.getBirthDate());
        person.setDeathDate(personEntity.getDeathDate());
        person.setDescription(personEntity.getDescription());
        person.setImageUrl(personEntity.getImageUrl());
        person.setWikiUrl(personEntity.getWikiUrl());
        person.setCategories(categoryMapper.mapToDomain(personEntity.getCategories()));
        return person;
    }

    public PersonEntity map(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(person.getId());
        personEntity.setName(person.getName());
        personEntity.setSurname(person.getSurname());
        personEntity.setBirthDate(person.getBirthDate());
        personEntity.setDeathDate(person.getDeathDate());
        personEntity.setDescription(person.getDescription());
        personEntity.setImageUrl(person.getImageUrl());
        personEntity.setWikiUrl(person.getWikiUrl());
        personEntity.setCategories(categoryMapper.map(person.getCategories()));
        return personEntity;
    }

    public List<Person> map(List<PersonEntity> personEntities) {
        return personEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
