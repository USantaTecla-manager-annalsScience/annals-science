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

    public static Entity getEntity() {
        Entity entity = new Entity();
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
}
