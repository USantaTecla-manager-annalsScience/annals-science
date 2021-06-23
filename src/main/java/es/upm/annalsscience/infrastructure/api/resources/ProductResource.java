package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.domain.model.Product;
import es.upm.annalsscience.domain.services.ProductService;
import es.upm.annalsscience.infrastructure.api.dto.CreateProductDTO;
import es.upm.annalsscience.infrastructure.api.dto.ProductDTO;
import es.upm.annalsscience.infrastructure.api.mappers.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("products")
public class ProductResource {

    private final ProductDTOMapper productDTOMapper;
    private final ProductService productService;

    @Autowired
    public ProductResource(ProductDTOMapper productDTOMapper,
                           ProductService productService) {
        this.productDTOMapper = productDTOMapper;
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Product product = this.productService.create(productDTOMapper.map(createProductDTO));
        return ResponseEntity.ok(productDTOMapper.map(product));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(productDTOMapper.map(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> find(@RequestParam(required = false) String category) {
        List<Product> products;
        if(category == null) products = productService.findAll();
        else products = productService.findByCategory(category);
        return ResponseEntity.ok(productDTOMapper.map(products));
    }

    @PutMapping("{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<ProductDTO> update(@RequestBody CreateProductDTO createProductDTO, @PathVariable Long id) {
        Product product = productService.update(id, productDTOMapper.map(createProductDTO));
        return ResponseEntity.ok(productDTOMapper.map(product));
    }
}
