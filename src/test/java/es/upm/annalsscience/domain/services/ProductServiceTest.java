package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.exceptions.EntityNotExistException;
import es.upm.annalsscience.domain.exceptions.PersonNotExistException;
import es.upm.annalsscience.domain.exceptions.ProductNotExistException;
import es.upm.annalsscience.domain.model.*;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import es.upm.annalsscience.domain.repositories.EntityRepository;
import es.upm.annalsscience.domain.repositories.PersonRepository;
import es.upm.annalsscience.domain.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private EntityRepository entityRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        //GIVEN
        CreateProduct createProduct = Utils.getCreateProduct();
        Product expectedProduct = Utils.getProduct();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getPerson()));
        when(entityRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getEntity()));
        when(productRepository.save(createProduct))
                .thenReturn(expectedProduct);
        //WHEN
        Product product = productService.create(createProduct);

        //THEN
        assertEquals(expectedProduct, product);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInCreateProduct() {
        //GIVEN
        CreateProduct createProduct = Utils.getCreateProduct();
        when(categoryRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            productService.create(createProduct);
        });
    }

    @Test
    void shouldThrowExceptionWhenPersonNotExistInCreateProduct() {
        //GIVEN
        CreateProduct createProduct = Utils.getCreateProduct();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(PersonNotExistException.class, () -> {
            productService.create(createProduct);
        });
    }

    @Test
    void shouldThrowExceptionWhenEntityNotExistInCreateProduct() {
        //GIVEN
        CreateProduct createProduct = Utils.getCreateProduct();
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getPerson()));
        when(entityRepository.findAll())
                .thenReturn(Collections.emptyList());
        //WHEN
        assertThrows(EntityNotExistException.class, () -> {
            productService.create(createProduct);
        });
    }

    @Test
    void shouldFindById() {
        //GIVEN
        Long id = 1L;
        Optional<Product> expectedProduct = Optional.of(Utils.getProduct());
        when(productRepository.findById(id))
                .thenReturn(expectedProduct);
        //WHEN
        Optional<Product> product = productService.findById(id);
        //THEN
        assertEquals(expectedProduct, product);
    }

    @Test
    void shouldFindAll() {
        //GIVEN
        List<Product> expectedProducts = Arrays.asList(Utils.getProduct());
        when(productRepository.findAll())
                .thenReturn(expectedProducts);
        //WHEN
        List<Product> products = productService.findAll();
        //THEN
        assertEquals(expectedProducts, products);
    }

    @Test
    void shouldDelete() {
        //GIVEN
        Long id = 1L;
        Optional<Product> expectedProduct = Optional.of(Utils.getProduct());
        when(productRepository.findById(id))
                .thenReturn(expectedProduct);
        //WHEN
        productService.delete(id);
        //THEN
        verify(productRepository, times(1)).delete(expectedProduct.get());
    }

    @Test
    void shouldThrowExceptionWhenProductNotExistInDelete() {
        //GIVEN
        Long id = 1L;
        when(productRepository.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(ProductNotExistException.class, () -> {
            productService.delete(id);
        });
    }

    @Test
    void shouldFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        Category category = Utils.getCategory();
        List<Product> expectedProduct = Arrays.asList(Utils.getProduct());
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category))
                .thenReturn(expectedProduct);
        //WHEN
        List<Product> products = productService.findByCategory(categoryName);

        //THEN
        assertEquals(expectedProduct, products);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInFindByCategory() {
        //GIVEN
        String categoryName = "categoryName";
        when(categoryRepository.findByName(categoryName))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            productService.findByCategory(categoryName);
        });
    }

    @Test
    void shouldUpdate() {
        //GIVEN
        Long id = 1L;
        Optional<Product> expectedProduct = Optional.of(Utils.getProduct());
        CreateProduct createProduct = Utils.getCreateProduct();
        when(productRepository.findById(id))
                .thenReturn(expectedProduct);
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getCategory()));
        when(personRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getPerson()));
        when(entityRepository.findAll())
                .thenReturn(Arrays.asList(Utils.getEntity()));
        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(Utils.getCategory()));
        when(personRepository.findById(id))
                .thenReturn(Optional.of(Utils.getPerson()));
        when(entityRepository.findById(id))
                .thenReturn(Optional.of(Utils.getEntity()));
        when(productRepository.update(expectedProduct.get()))
                .thenReturn(expectedProduct.get());

        //WHEN
        Product product = productService.update(id, createProduct);

        //THEN
        assertEquals(expectedProduct.get(), product);
    }

    @Test
    void shouldThrowExceptionWhenEntityNotExistInUpdate(){
        //GIVEN
        Long id = 1L;
        CreateProduct createProduct = Utils.getCreateProduct();
        when(productService.findById(id))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(ProductNotExistException.class, () -> {
            productService.update(id, createProduct);
        });
    }
}