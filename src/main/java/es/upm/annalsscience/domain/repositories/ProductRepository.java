package es.upm.annalsscience.domain.repositories;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateProduct;
import es.upm.annalsscience.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(CreateProduct createProduct);
    Optional<Product> findById(Long id);
    void delete(Product product);
    List<Product> findAll();
    List<Product> findByCategory(Category category);
    Product update(Product product);
}
