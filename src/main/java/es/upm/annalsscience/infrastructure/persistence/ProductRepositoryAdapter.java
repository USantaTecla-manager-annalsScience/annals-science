package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateProduct;
import es.upm.annalsscience.domain.model.Product;
import es.upm.annalsscience.domain.repositories.ProductRepository;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.ProductDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.CategoryMapper;
import es.upm.annalsscience.infrastructure.persistence.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductDAO productDAO;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductRepositoryAdapter(ProductDAO productDAO, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productDAO = productDAO;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Product save(CreateProduct createProduct) {
        ProductEntity productEntity = productMapper.map(createProduct);
        return productMapper.map(this.productDAO.save(productEntity));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productDAO.findById(id)
                .map(productMapper::map);
    }

    @Override
    public void delete(Product product) {
        this.productDAO.delete(productMapper.map(product));
    }

    @Override
    public List<Product> findAll() {
        return productMapper.map(productDAO.findAll());
    }

    @Override
    public List<Product> findByCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.map(category);
        return productMapper.map(productDAO.findByCategories(categoryEntity));
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = productMapper.map(product);
        return productMapper.map(productDAO.save(productEntity));
    }
}
