package com.Sahil.inventory_management;

import com.Sahil.inventory_management.DTO.ProductDTO;
import com.Sahil.inventory_management.Exception.ResourceNotFoundException;
import com.Sahil.inventory_management.Repository.ProductRespository;
import com.Sahil.inventory_management.Repository.TransactionLogRepository;
import com.Sahil.inventory_management.ServiceImpl.ProductServiceImpl;
import com.Sahil.inventory_management.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductServiceImplTest {


    @Mock
    private ProductRespository productRespository;

    @Mock
    private TransactionLogRepository transactionLogRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Test Product");
        sampleProduct.setQuantity(10);
        sampleProduct.setPrice(100.0);
        sampleProduct.setCategory("Electronics");
        sampleProduct.setCreatedAt(LocalDateTime.now());
        sampleProduct.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testGetProductById_Success() {
        when(productRespository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRespository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRespository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(2L));
        verify(productRespository, times(1)).findById(2L);
    }

    @Test
    void testUpdateStockQuantity_Increase() {
        when(productRespository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRespository.save(any(Product.class))).thenReturn(sampleProduct);

        productService.updateStockQuantity(1L, 5);

        assertEquals(15, sampleProduct.getQuantity());
        verify(transactionLogRepository, times(1)).save(any());
    }

    @Test
    void testUpdateStockQuantity_Decrease() {
        when(productRespository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRespository.save(any(Product.class))).thenReturn(sampleProduct);

        productService.updateStockQuantity(1L, -5);

        assertEquals(5, sampleProduct.getQuantity());
        verify(transactionLogRepository, times(1)).save(any());
    }

    @Test
    void testUpdateStockQuantity_NegativeStock() {
        when(productRespository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        assertThrows(RuntimeException.class, () -> productService.updateStockQuantity(1L, -20));
    }
}



