package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<Category> mapToDomain(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<CategoryEntity> map(List<Category> categories) {
        return categories.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public CategoryEntity map(CreateCategory createCategory) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(createCategory.getName());
        categoryEntity.setParentId(createCategory.getParentId());
        return categoryEntity;
    }

    public CategoryEntity map(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setName(category.getName());
        categoryEntity.setParentId(category.getParentId());
        return categoryEntity;
    }

    public Category map(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getParentId());
    }
}
