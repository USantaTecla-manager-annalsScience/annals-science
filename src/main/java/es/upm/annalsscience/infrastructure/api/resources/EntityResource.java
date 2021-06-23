package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.domain.model.Entity;
import es.upm.annalsscience.domain.services.EntityService;
import es.upm.annalsscience.infrastructure.api.dto.CreateEntityDTO;
import es.upm.annalsscience.infrastructure.api.dto.EntityDTO;
import es.upm.annalsscience.infrastructure.api.mappers.EntityDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("entities")
public class EntityResource {

    private final EntityDTOMapper entityDTOMapper;
    private final EntityService entityService;

    @Autowired
    public EntityResource(EntityDTOMapper entityDTOMapper,
                          EntityService entityService) {
        this.entityDTOMapper = entityDTOMapper;
        this.entityService = entityService;
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<EntityDTO> createEntity(@RequestBody CreateEntityDTO createEntityDTO) {
        Entity entity = this.entityService.create(entityDTOMapper.map(createEntityDTO));
        return ResponseEntity.ok(entityDTOMapper.map(entity));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityDTO> findById(@PathVariable Long id) {
        return entityService.findById(id)
                .map(entity -> ResponseEntity.ok(entityDTOMapper.map(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entityService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<EntityDTO>> find(@RequestParam(required = false) String category) {
        List<Entity> entities;
        if(category == null) entities = entityService.findAll();
        else entities = entityService.findByCategory(category);
        return ResponseEntity.ok(entityDTOMapper.map(entities));
    }

    @PutMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<EntityDTO> update(@RequestBody CreateEntityDTO createEntityDTO, @PathVariable Long id) {
        Entity entity = entityService.update(id, entityDTOMapper.map(createEntityDTO));
        return ResponseEntity.ok(entityDTOMapper.map(entity));
    }
}
