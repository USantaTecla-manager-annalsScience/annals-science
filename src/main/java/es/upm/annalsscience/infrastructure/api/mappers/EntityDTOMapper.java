package es.upm.annalsscience.infrastructure.api.mappers;

import es.upm.annalsscience.domain.model.CreateEntity;
import es.upm.annalsscience.domain.model.Entity;
import es.upm.annalsscience.infrastructure.api.dto.CreateEntityDTO;
import es.upm.annalsscience.infrastructure.api.dto.EntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDTOMapper {

    private final CategoryDTOMapper categoryDTOMapper;
    private final PersonDTOMapper personDTOMapper;

    @Autowired
    public EntityDTOMapper(CategoryDTOMapper categoryDTOMapper,
                           PersonDTOMapper personDTOMapper) {
        this.categoryDTOMapper = categoryDTOMapper;
        this.personDTOMapper = personDTOMapper;
    }

    public CreateEntity map(CreateEntityDTO createEntityDTO) {
        CreateEntity createEntity = new CreateEntity();
        createEntity.setName(createEntityDTO.getName());
        createEntity.setCreationDate(createEntityDTO.getCreationDate());
        createEntity.setEndDate(createEntityDTO.getEndDate());
        createEntity.setDescription(createEntityDTO.getDescription());
        createEntity.setImageUrl(createEntityDTO.getImageUrl());
        createEntity.setWikiUrl(createEntityDTO.getWikiUrl());
        createEntity.setCategoriesId(createEntityDTO.getCategoriesId() == null ? Collections.emptyList() : createEntityDTO.getCategoriesId());
        createEntity.setPersonsId(createEntityDTO.getPersonsId() == null ? Collections.emptyList() : createEntityDTO.getPersonsId());
        return createEntity;
    }

    public EntityDTO map(Entity entity) {
        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setId(entity.getId());
        entityDTO.setName(entity.getName());
        entityDTO.setCreationDate(entity.getCreationDate());
        entityDTO.setEndDate(entity.getEndDate());
        entityDTO.setDescription(entity.getDescription());
        entityDTO.setImageUrl(entity.getImageUrl());
        entityDTO.setWikiUrl(entity.getWikiUrl());
        entityDTO.setCategoriesId(categoryDTOMapper.map(entity.getCategories()));
        entityDTO.setPersonsId(personDTOMapper.map(entity.getPersons()));
        return entityDTO;
    }

    public List<EntityDTO> map(List<Entity> entityList) {
        return entityList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
