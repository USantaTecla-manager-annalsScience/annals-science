package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryRepositoryAdapter(CategoryDAO categoryDAO, CategoryMapper categoryMapper) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryDAO.findById(id)
                .map(categoryMapper::map);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return this.categoryDAO.findByName(name)
                .map(categoryMapper::map);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.mapToDomain(this.categoryDAO.findAll());
    }

    @Override
    public Category save(CreateCategory createCategory) {
        return categoryMapper.map(this.categoryDAO.save(categoryMapper.map(createCategory)));
    }

    @Override
    public void delete(Category category) {
        CategoryEntity categoryEntity = categoryMapper.map(category);
        List<CategoryEntity> childrenCategories = categoryDAO.findByParentId(category.getId())
                .stream()
                .peek(ce -> ce.setParentId(null))
                .collect(Collectors.toList());
        this.categoryDAO.saveAll(childrenCategories);
        this.categoryDAO.delete(categoryEntity);
    }
}
