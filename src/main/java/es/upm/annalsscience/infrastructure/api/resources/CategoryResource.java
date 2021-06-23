package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.services.CategoryService;
import es.upm.annalsscience.infrastructure.api.dto.CategoryDTO;
import es.upm.annalsscience.infrastructure.api.dto.CreateCategoryDTO;
import es.upm.annalsscience.infrastructure.api.mappers.CategoryDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("categories")
public class CategoryResource {

    private final CategoryService categoryService;
    private final CategoryDTOMapper categoryDTOMapper;

    @Autowired
    public CategoryResource(CategoryService categoryService, CategoryDTOMapper categoryDTOMapper) {
        this.categoryService = categoryService;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<CategoryDTO> create(@RequestBody CreateCategoryDTO createCategoryDTO) {
        Category category = categoryService.create(categoryDTOMapper.map(createCategoryDTO));
        return ResponseEntity.ok(categoryDTOMapper.map(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categoryDTOMapper.map(categories));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
