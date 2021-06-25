package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;

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
}
