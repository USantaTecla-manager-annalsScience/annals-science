package es.upm.annalsscience.infrastructure.api.mappers;

import es.upm.annalsscience.domain.model.CreatePerson;
import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.infrastructure.api.dto.CreatePersonDTO;
import es.upm.annalsscience.infrastructure.api.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonDTOMapper {

    private final CategoryDTOMapper categoryDTOMapper;

    @Autowired
    public PersonDTOMapper(CategoryDTOMapper categoryDTOMapper) {
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public CreatePerson map(CreatePersonDTO createPersonDTO) {
        CreatePerson createPerson = new CreatePerson();
        createPerson.setName(createPersonDTO.getName());
        createPerson.setSurname(createPersonDTO.getSurname());
        createPerson.setBirthDate(createPersonDTO.getBirthDate());
        createPerson.setDeathDate(createPersonDTO.getDeathDate());
        createPerson.setDescription(createPersonDTO.getDescription());
        createPerson.setImageUrl(createPersonDTO.getImageUrl());
        createPerson.setWikiUrl(createPersonDTO.getWikiUrl());
        createPerson.setCategoriesId(createPersonDTO.getCategoriesId() == null ? Collections.emptyList() : createPersonDTO.getCategoriesId());
        return createPerson;
    }

    public PersonDTO map(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setBirthDate(person.getBirthDate());
        personDTO.setDeathDate(person.getDeathDate());
        personDTO.setDescription(person.getDescription());
        personDTO.setImageUrl(person.getImageUrl());
        personDTO.setWikiUrl(person.getWikiUrl());
        personDTO.setCategories(categoryDTOMapper.map(person.getCategories()));
        return personDTO;
    }

    public List<PersonDTO> map(List<Person> personList) {
        return personList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
