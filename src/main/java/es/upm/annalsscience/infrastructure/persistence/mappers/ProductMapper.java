package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.CreateProduct;
import es.upm.annalsscience.domain.model.Product;
import es.upm.annalsscience.infrastructure.persistence.entities.CategoryEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.EntityEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.PersonEntity;
import es.upm.annalsscience.infrastructure.persistence.entities.ProductEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.CategoryDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.EntityDAO;
import es.upm.annalsscience.infrastructure.persistence.jpa.PersonDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;
    private final PersonDAO personDAO;
    private final PersonMapper personMapper;
    private final EntityDAO entityDAO;
    private final EntityMapper entityMapper;

    public ProductMapper(CategoryDAO categoryDAO,
                         CategoryMapper categoryMapper,
                         PersonDAO personDAO,
                         PersonMapper personMapper,
                         EntityDAO entityDAO,
                         EntityMapper entityMapper) {

        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
        this.personDAO = personDAO;
        this.personMapper = personMapper;
        this.entityDAO = entityDAO;
        this.entityMapper = entityMapper;
    }

    public ProductEntity map(CreateProduct createProduct) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProduct.getName());
        productEntity.setCreationDate(createProduct.getCreationDate());
        productEntity.setEndDate(createProduct.getEndDate());
        productEntity.setDescription(createProduct.getDescription());
        productEntity.setImageUrl(createProduct.getImageUrl());
        productEntity.setWikiUrl(createProduct.getWikiUrl());
        productEntity.setCategories(retrieveCategories(createProduct.getCategoriesId()));
        productEntity.setPersons(retrievePersons(createProduct.getPersonsId()));
        productEntity.setEntities(retrieveEntities(createProduct.getEntitiesId()));
        return productEntity;
    }

    private List<CategoryEntity> retrieveCategories(List<Long> categoriesId) {
        return categoriesId
                .stream()
                .map(id -> this.categoryDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    private List<PersonEntity> retrievePersons(List<Long> categoriesId) {
        return categoriesId
                .stream()
                .map(id -> this.personDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    private List<EntityEntity> retrieveEntities(List<Long> entitiesId) {
        return entitiesId
                .stream()
                .map(id -> this.entityDAO.findById(id)
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
    }

    public Product map(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setCreationDate(productEntity.getCreationDate());
        product.setEndDate(productEntity.getEndDate());
        product.setDescription(productEntity.getDescription());
        product.setImageUrl(productEntity.getImageUrl());
        product.setWikiUrl(productEntity.getWikiUrl());
        product.setCategories(categoryMapper.mapToDomain(productEntity.getCategories()));
        product.setPersons(personMapper.map(productEntity.getPersons()));
        product.setEntities(entityMapper.map(productEntity.getEntities()));
        return product;
    }

    public ProductEntity map(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setCreationDate(product.getCreationDate());
        productEntity.setEndDate(product.getEndDate());
        productEntity.setDescription(product.getDescription());
        productEntity.setImageUrl(product.getImageUrl());
        productEntity.setWikiUrl(product.getWikiUrl());
        productEntity.setCategories(categoryMapper.map(product.getCategories()));
        productEntity.setPersons(personMapper.mapToEntities(product.getPersons()));
        productEntity.setEntities(entityMapper.mapToEntities(product.getEntities()));
        return productEntity;
    }

    public List<Product> map(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<ProductEntity> mapToEntities(List<Product> products) {
        return products.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
