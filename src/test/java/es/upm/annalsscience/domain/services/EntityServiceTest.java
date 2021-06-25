package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.EntityNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.model.*;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.EntityRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private EntityRepository entityRepository;

    @InjectMocks
    private EntityService entityService;

    @Test
    void shouldCreateEntity() {
        //GIVEN
        CreateEntity createEntity = Utils.getCreateEntity();
        Entity expectedEntity = Utils.getEntity();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getPerson()));
        when(entityRepository.save(createEntity))
                .thenReturn(expectedEntity);
        //WHEN
        Entity entity = entityService.create(createEntity);

        //THEN
        assertEquals(expectedEntity, entity);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInCreateEntity() {
        //GIVEN
        CreateEntity createEntity = Utils.getCreateEntity();
        when(categoryRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            entityService.create(createEntity);
        });
    }

    @Test
    void shouldThrowExceptionWhenPersonNotExistInCreateEntity() {
        //GIVEN
        CreateEntity createEntity = Utils.getCreateEntity();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(PersonNotExistException.class, () -> {
            entityService.create(createEntity);
        });
    }

    @Test
    void shouldFindById() {
        //GIVEN
        Long id = 1L;
        Optional<Entity> expectedEntity = Optional.of(Utils.getEntity());
        when(entityRepository.findById(id))
                .thenReturn(expectedEntity);
        //WHEN
        Optional<Entity> entity = entityService.findById(id);
        //THEN
        assertEquals(expectedEntity, entity);
    }

    @Test
    void shouldFindAll() {
        //GIVEN
        List<Entity> expectedEntity = Arrays.asList(Utils.getEntity());
        when(entityRepository.findAll())
                .thenReturn(expectedEntity);
        //WHEN
        List<Entity> entities = entityService.findAll();
        //THEN
        assertEquals(expectedEntity, entities);
    }

    @Test
    void shouldDelete() {
        //GIVEN
        Long id = 1L;
        Optional<Entity> expectedEntity = Optional.of(Utils.getEntity());
        when(entityRepository.findById(id))
                .thenReturn(expectedEntity);
        //WHEN
        entityService.delete(id);
        //THEN
        verify(entityRepository, times(1)).delete(expectedEntity.get());
    }

    @Test
    public void shouldThrowExceptionWhenEntityNotExistInDelete() {
        //GIVEN
        Long id = 1L;
        when(entityRepository.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(EntityNotExistException.class, () -> {
            entityService.delete(id);
        });
    }

    @Test
    void shouldFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        Category category = Utils.getCategory();
        List<Entity> expectedEntity = Arrays.asList(Utils.getEntity());
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.of(category));
        when(entityRepository.findByCategory(category))
                .thenReturn(expectedEntity);
        //WHEN
        List<Entity> entities = entityService.findByCategory(categoryName);

        //THEN
        assertEquals(expectedEntity, entities);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            entityService.findByCategory(categoryName);
        });
    }

    @Test
    void shouldUpdate() {
        //GIVEN
        Long id = 1L;
        Optional<Entity> expectedEntity = Optional.of(Utils.getEntity());
        CreateEntity createEntity = Utils.getCreateEntity();
        when(entityRepository.findById(id))
                .thenReturn(expectedEntity);
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getPerson()));
        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(Utils.getCategory()));
        when(personRepository.findById(id))
                .thenReturn(Optional.of(Utils.getPerson()));
        when(entityRepository.update(expectedEntity.get()))
                .thenReturn(expectedEntity.get());

        //WHEN
        Entity entity = entityService.update(id, createEntity);

        //THEN
        assertEquals(expectedEntity.get(), entity);
    }

    @Test
    void shouldThrowExceptionWhenEntityNotExistInUpdate(){
        //GIVEN
        Long id = 1L;
        CreateEntity createEntity = Utils.getCreateEntity();
        when(entityService.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(PersonNotExistException.class, () -> {
            entityService.update(id, createEntity);
        });
    }
}