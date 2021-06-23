package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryMapper(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

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
        if(category.getParent() != null)
            categoryEntity.setParentId(category.getParent().getId());
        return categoryEntity;
    }

    public Category map(CategoryEntity categoryEntity) {
        Category category = new Category();

        if(categoryEntity.getParentId() != null) {
            CategoryEntity parent = categoryDAO.getById(categoryEntity.getParentId());
            category.setParent(mapToRelated(parent));
        }

        List<CategoryEntity> children = categoryDAO.findByParentId(categoryEntity.getId());
        category.setChildren(mapToRelated(children));

        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        return category;
    }

    Category.RelatedCategory mapToRelated(CategoryEntity categoryEntity) {
        Category.RelatedCategory relatedCategory = new Category.RelatedCategory();
        relatedCategory.setId(categoryEntity.getId());
        relatedCategory.setName(categoryEntity.getName());
        return relatedCategory;
    }

    List<Category.RelatedCategory> mapToRelated(List<CategoryEntity> categories) {
        return categories.stream()
                .map(this::mapToRelated)
                .collect(Collectors.toList());
    }
}
