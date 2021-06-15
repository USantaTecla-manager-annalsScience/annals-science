package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(CategoryRepository categoryRepository, PersonRepository personRepository) {
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }

    public Person create(CreatePerson createPerson) {
        checkIfAllCategoriesExist(createPerson.getCategoriesId());
        return personRepository.save(createPerson);
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotExistException("Person with id: " + id + " not exist"));
        personRepository.delete(person);
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

    public List<Person> findByCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotExistException("Category with name " + name + " not exist"));
        return personRepository.findByCategory(category);
    }
}
