package com.Sahil.inventory_management.Service;

import com.Sahil.inventory_management.DTO.ProductDTO;
import com.Sahil.inventory_management.model.Product;

import java.util.List;

public interface IProductService {

    // ---------------- Product CRUD ----------------
    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    List<ProductDTO> getProducts(int page, int size, String category, String brand, Double minPrice, Double maxPrice);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    // ---------------- Delete Product ----------------
    void deleteProduct(Long id);

    // ---------------- Stock Management ----------------
    Product updateStockQuantity(Long id, Integer quantity);

    // ---------------- Low Stock Reports ----------------
    List<ProductDTO> getLowStockProducts();


}

