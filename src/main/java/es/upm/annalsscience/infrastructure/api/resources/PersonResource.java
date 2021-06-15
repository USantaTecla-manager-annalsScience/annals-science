package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.domain.model.Person;
import es.upm.annalsscience.domain.services.PersonService;
import es.upm.annalsscience.infrastructure.api.dto.CreatePersonDTO;
import es.upm.annalsscience.infrastructure.api.dto.PersonDTO;
import es.upm.annalsscience.infrastructure.api.mappers.PersonDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("persons")
public class PersonResource {

    private final PersonService personService;
    private final PersonDTOMapper personDTOMapper;

    @Autowired
    public PersonResource(PersonService personService, PersonDTOMapper personDTOMapper) {
        this.personService = personService;
        this.personDTOMapper = personDTOMapper;
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody CreatePersonDTO createPersonDTO) {
        Person person = this.personService.create(personDTOMapper.map(createPersonDTO));
        return ResponseEntity.ok(personDTOMapper.map(person));
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<Person> personList = this.personService.findAll();
        return ResponseEntity.ok(personDTOMapper.map(personList));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        return personService.findById(id)
                .map(person -> ResponseEntity.ok(personDTOMapper.map(person)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "category")
    public ResponseEntity<List<PersonDTO>> findById(@RequestParam("category") String category) {
        List<Person> persons = personService.findByCategory(category);
        return ResponseEntity.ok(personDTOMapper.map(persons));
    }
}
