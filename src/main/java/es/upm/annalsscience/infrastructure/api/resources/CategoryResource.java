package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.services.CategoryService;
import es.upm.annalsscience.infrastructure.api.dto.CreateCategoryDTO;
import es.upm.annalsscience.infrastructure.api.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("categories")
public class CategoryResource {

    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<CategoryDTO> create(@RequestBody CreateCategoryDTO createCategoryDTO) {
        Category category = categoryService.create(map(createCategoryDTO));
        return ResponseEntity.ok(map(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(map(categories));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    private List<CategoryDTO> map(List<Category> categories) {
        return categories.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CreateCategory map(CreateCategoryDTO createCategoryDTO) {
        return new CreateCategory(createCategoryDTO.getName(), createCategoryDTO.getParentId());
    }

    private CategoryDTO map(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getParentId());
    }

}
