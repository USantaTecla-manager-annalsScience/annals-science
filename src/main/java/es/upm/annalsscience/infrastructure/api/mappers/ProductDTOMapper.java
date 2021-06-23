package es.upm.annalsscience.infrastructure.api.mappers;

import es.upm.annalsscience.domain.model.CreateProduct;
import es.upm.annalsscience.domain.model.Product;
import es.upm.annalsscience.infrastructure.api.dto.CreateProductDTO;
import es.upm.annalsscience.infrastructure.api.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOMapper {

    private final CategoryDTOMapper categoryDTOMapper;
    private final PersonDTOMapper personDTOMapper;
    private final EntityDTOMapper entityDTOMapper;

    @Autowired
    public ProductDTOMapper(CategoryDTOMapper categoryDTOMapper,
                            PersonDTOMapper personDTOMapper,
                            EntityDTOMapper entityDTOMapper) {
        this.categoryDTOMapper = categoryDTOMapper;
        this.personDTOMapper = personDTOMapper;
        this.entityDTOMapper = entityDTOMapper;
    }

    public CreateProduct map(CreateProductDTO createProductDTO) {
        CreateProduct createProduct = new CreateProduct();
        createProduct.setName(createProductDTO.getName());
        createProduct.setCreationDate(createProductDTO.getCreationDate());
        createProduct.setEndDate(createProductDTO.getEndDate());
        createProduct.setDescription(createProductDTO.getDescription());
        createProduct.setImageUrl(createProductDTO.getImageUrl());
        createProduct.setWikiUrl(createProductDTO.getWikiUrl());
        createProduct.setCategoriesId(createProductDTO.getCategoriesId() == null ? Collections.emptyList() : createProductDTO.getCategoriesId());
        createProduct.setPersonsId(createProductDTO.getPersonsId() == null ? Collections.emptyList() : createProductDTO.getPersonsId());
        createProduct.setEntitiesId(createProductDTO.getEntitiesId() == null ? Collections.emptyList() : createProductDTO.getEntitiesId());
        return createProduct;
    }

    public ProductDTO map(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCreationDate(product.getCreationDate());
        productDTO.setEndDate(product.getEndDate());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setWikiUrl(product.getWikiUrl());
        productDTO.setCategories(categoryDTOMapper.map(product.getCategories()));
        productDTO.setPersons(personDTOMapper.map(product.getPersons()));
        productDTO.setEntities(entityDTOMapper.map(product.getEntities()));
        return productDTO;
    }

    public List<ProductDTO> map(List<Product> products) {
        return products.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
