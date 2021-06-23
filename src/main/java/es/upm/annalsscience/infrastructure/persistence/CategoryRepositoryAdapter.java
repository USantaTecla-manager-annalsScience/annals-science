package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.EntityDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.PersonDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.ProductDAO;
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
    private final PersonDAO personDAO;
    private final EntityDAO entityDAO;
    private final ProductDAO productDAO;

    @Autowired
    public CategoryRepositoryAdapter(CategoryDAO categoryDAO,
                                     CategoryMapper categoryMapper,
                                     PersonDAO personDAO,
                                     EntityDAO entityDAO,
                                     ProductDAO productDAO) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
        this.personDAO = personDAO;
        this.entityDAO = entityDAO;
        this.productDAO = productDAO;
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

        List<PersonEntity> personsWithCategoryToDelete = personDAO.findByCategories(categoryEntity)
                .stream()
                .peek(personEntity -> personEntity.getCategories().remove(categoryEntity))
                .collect(Collectors.toList());
        personsWithCategoryToDelete.forEach(personDAO::save);

        List<EntityEntity> entitiesWithCategoryToDelete = entityDAO.findByCategories(categoryEntity)
                .stream()
                .peek(entityEntity -> entityEntity.getCategories().remove(categoryEntity))
                .collect(Collectors.toList());
        entitiesWithCategoryToDelete.forEach(entityDAO::save);

        List<ProductEntity> productsWithCategoryToDelete = productDAO.findByCategories(categoryEntity)
                .stream()
                .peek(entityEntity -> entityEntity.getCategories().remove(categoryEntity))
                .collect(Collectors.toList());
        productsWithCategoryToDelete.forEach(productDAO::save);

        this.categoryDAO.saveAll(childrenCategories);
        this.categoryDAO.delete(categoryEntity);
    }
}
