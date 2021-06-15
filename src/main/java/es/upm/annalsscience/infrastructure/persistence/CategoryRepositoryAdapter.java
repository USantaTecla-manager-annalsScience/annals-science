package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryRepositoryAdapter(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryDAO.findById(id)
                .map(this::map);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        return map(this.categoryDAO.findAll());
    }

    @Override
    public Category save(CreateCategory createCategory) {
        return map(this.categoryDAO.save(map(createCategory)));
    }

    @Override
    public void delete(Category category) {
        CategoryEntity categoryEntity = map(category);
        this.categoryDAO.delete(categoryEntity);
    }

    private List<Category> map(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
    private CategoryEntity map(CreateCategory createCategory) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(createCategory.getName());
        categoryEntity.setParentId(createCategory.getParentId());
        return categoryEntity;
    }

    private CategoryEntity map(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setName(category.getName());
        categoryEntity.setParentId(category.getParentId());
        return categoryEntity;
    }

    private Category map(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getParentId());
    }
}
