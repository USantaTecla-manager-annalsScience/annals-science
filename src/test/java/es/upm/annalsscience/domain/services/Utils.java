package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.model.*;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static Category getCategory() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        return expectedCategory;
    }

    public static Person getPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setCategories(Arrays.asList(getCategory()));
        return person;
    }

    public static CreatePerson getCreatePerson() {
        CreatePerson createPerson = new CreatePerson();
        createPerson.setCategoriesId(getCategoriesId());
        return createPerson;
    }

    public static List<Long> getCategoriesId() {
        return Arrays.asList(1L);
    }

    public static List<Long> getPersonsId() {
        return Arrays.asList(1L);
    }

    public static List<Long> getEntitiesId() {
        return Arrays.asList(1L);
    }

    public static Entity getEntity() {
        Entity entity = new Entity();
        entity.setId(1L);
        entity.setCategories(Arrays.asList(getCategory()));
        entity.setPersons(Arrays.asList(getPerson()));
        return entity;
    }

    public static CreateEntity getCreateEntity() {
        CreateEntity createEntity = new CreateEntity();
        createEntity.setCategoriesId(getCategoriesId());
        createEntity.setPersonsId(getPersonsId());
        return createEntity;
    }

    public static Product getProduct() {
        Product product = new Product();
        product.setCategories(Arrays.asList(getCategory()));
        product.setPersons(Arrays.asList(getPerson()));
        product.setEntities(Arrays.asList(getEntity()));
        return product;
    }

    public static CreateProduct getCreateProduct() {
        CreateProduct createProduct = new CreateProduct();
        createProduct.setCategoriesId(getCategoriesId());
        createProduct.setPersonsId(getPersonsId());
        createProduct.setEntitiesId(getEntitiesId());
        return createProduct;
    }
}
