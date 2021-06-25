package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldCreatePerson() {
        //GIVEN
        CreatePerson createPerson = Utils.getCreatePerson();
        Person expectedPerson = Utils.getPerson();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.save(createPerson))
                .thenReturn(expectedPerson);
        //WHEN
        Person person = personService.create(createPerson);

        //THEN
        assertEquals(expectedPerson, person);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExist() {
        //GIVEN
        CreatePerson createPerson = Utils.getCreatePerson();
        when(categoryRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            personService.create(createPerson);
        });
    }

    @Test
    void shouldFindById() {
        //GIVEN
        Long id = 1L;
        Optional<Person> expectedPerson = Optional.of(Utils.getPerson());
        when(personRepository.findById(id))
                .thenReturn(expectedPerson);
        //WHEN
        Optional<Person> person = personService.findById(id);
        //THEN
        assertEquals(expectedPerson, person);
    }

    @Test
    void shouldFindAll() {
        //GIVEN
        List<Person> expectedPerson = Arrays.asList(Utils.getPerson());
        when(personRepository.findAll())
                .thenReturn(expectedPerson);
        //WHEN
        List<Person> person = personService.findAll();
        //THEN
        assertEquals(expectedPerson, person);
    }

    @Test
    void shouldDelete() {
        //GIVEN
        Long id = 1L;
        Optional<Person> expectedPerson = Optional.of(Utils.getPerson());
        when(personRepository.findById(id))
                .thenReturn(expectedPerson);
        //WHEN
        personService.delete(id);
        //THEN
        verify(personRepository, times(1)).delete(expectedPerson.get());
    }

    @Test
    void shouldThrowExceptionWhenPersonNotExistInDelete() {
        //GIVEN
        Long id = 1L;
        when(personRepository.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(PersonNotExistException.class, () -> {
            personService.delete(id);
        });
    }

    @Test
    void shouldFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        Category category = Utils.getCategory();
        List<Person> expectedPerson = Arrays.asList(Utils.getPerson());
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.of(category));
        when(personRepository.findByCategory(category))
                .thenReturn(expectedPerson);
        //WHEN
        List<Person> persons = personService.findByCategory(categoryName);

        //THEN
        assertEquals(expectedPerson, persons);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            personService.findByCategory(categoryName);
        });
    }

    @Test
    void shouldUpdate() {
        //GIVEN
        Long id = 1L;
        Optional<Person> expectedPerson = Optional.of(Utils.getPerson());
        CreatePerson createPerson = Utils.getCreatePerson();
        when(personRepository.findById(id))
                .thenReturn(expectedPerson);
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(Utils.getCategory()));
        when(personRepository.update(expectedPerson.get()))
                .thenReturn(expectedPerson.get());

        //WHEN
        Person person = personService.update(id, createPerson);

        //THEN
        assertEquals(expectedPerson.get(), person);
    }

    @Test
    void shouldThrowExceptionWhenPersonNotExistInUpdate(){
        //GIVEN
        Long id = 1L;
        CreatePerson createPerson = Utils.getCreatePerson();
        when(personRepository.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(PersonNotExistException.class, () -> {
            personService.update(id, createPerson);
        });
    }
}