package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryAlreadyExistException;
import es.upm.annalsscience.domain.exceptions.CategoryIdNotExistException;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CreateCategory createCategory) {
        categoryRepository.findByName(createCategory.getName())
                .ifPresent(category -> {throw new CategoryAlreadyExistException("Category with name " + createCategory.getName() + " already exist");
                });

        if(createCategory.getParentId() != null) {
            categoryRepository.findById(createCategory.getParentId())
                    .orElseThrow(() -> new CategoryIdNotExistException("Category with id " + createCategory.getParentId() + " not exist"));
        }
        return categoryRepository.save(createCategory);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryIdNotExistException("Category with id " + id + " not exist"));
        categoryRepository.delete(category);
    }
}
