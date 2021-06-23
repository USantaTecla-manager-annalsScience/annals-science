package es.upm.annalsscience.infrastructure.api.mappers;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.infrastructure.api.dto.CategoryDTO;
import es.upm.annalsscience.infrastructure.api.dto.CreateCategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryDTOMapper {
    public List<CategoryDTO> map(List<Category> categories) {
        return categories.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public CreateCategory map(CreateCategoryDTO createCategoryDTO) {
        return new CreateCategory(createCategoryDTO.getName(), createCategoryDTO.getParentId());
    }

    public CategoryDTO map(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), map(category.getParent()), mapRelated(category.getChildren()));
    }

    public CategoryDTO.RelatedCategoryDTO map(Category.RelatedCategory relatedCategory) {
        if(relatedCategory == null) return null;
        CategoryDTO.RelatedCategoryDTO relatedCategoryDTO = new CategoryDTO.RelatedCategoryDTO();
        relatedCategoryDTO.setId(relatedCategory.getId());
        relatedCategoryDTO.setName(relatedCategory.getName());
        return relatedCategoryDTO;
    }

    public List<CategoryDTO.RelatedCategoryDTO> mapRelated(List<Category.RelatedCategory> relatedCategories) {
        return relatedCategories
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
