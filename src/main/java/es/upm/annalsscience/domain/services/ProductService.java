package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.EntityNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.exceptions.ProductNotExistException;
import es.upm.annalsscience.domain.model.*;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.EntityRepository;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import es.upm.annalsscience.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityRepository entityRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          EntityRepository entityRepository,
                          CategoryRepository categoryRepository,
                          PersonRepository personRepository) {
        this.productRepository = productRepository;
        this.entityRepository = entityRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }

    public Product create(CreateProduct createProduct) {
        checkIfAllCategoriesExist(createProduct.getCategoriesId());
        checkIfAllPersonsExist(createProduct.getPersonsId());
        checkIfAllEntitiesExist(createProduct.getEntitiesId());
        return productRepository.save(createProduct);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
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

    private void checkIfAllEntitiesExist(List<Long> entities) {
        if(!entityRepository.findAll()
                .stream()
                .map(Entity::getId)
                .collect(Collectors.toList())
                .containsAll(entities)) {
            throw new EntityNotExistException("Entity not exist");
        }
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("Product with id: " + id + " not exist"));
        productRepository.delete(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotExistException("Category with name " + name + " not exist"));
        return productRepository.findByCategory(category);
    }

    public Product update(Long id, CreateProduct updateProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("Product with id: " + id + " not exist"));
        checkIfAllCategoriesExist(updateProduct.getCategoriesId());
        checkIfAllPersonsExist(updateProduct.getPersonsId());
        checkIfAllEntitiesExist(updateProduct.getEntitiesId());
        product.setName(updateProduct.getName());
        product.setCreationDate(updateProduct.getCreationDate());
        product.setEndDate(updateProduct.getEndDate());
        product.setDescription(updateProduct.getDescription());
        product.setImageUrl(updateProduct.getImageUrl());
        product.setWikiUrl(updateProduct.getWikiUrl());
        product.setCategories(retrieveCategories(updateProduct.getCategoriesId()));
        product.setPersons(retrievePersons(updateProduct.getPersonsId()));
        product.setEntities(retrieveEntities(updateProduct.getEntitiesId()));
        return productRepository.update(product);
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

    private List<Entity> retrieveEntities(List<Long> entitiesId) {
        return entitiesId.stream()
                .map(entityRepository::findById)
                .map(entity -> entity.orElseThrow(() -> new EntityNotExistException("Entity not exist")))
                .collect(Collectors.toList());
    }
}
