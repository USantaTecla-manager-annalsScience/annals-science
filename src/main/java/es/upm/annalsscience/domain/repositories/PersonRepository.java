package es.upm.annalsscience.domain.repositories;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(Long id);
    Person save(CreatePerson createPerson);
    List<Person> findAll();
    List<Person> findByCategory(Category category);
    void delete(Person person);
    Person update(Person person);
}
