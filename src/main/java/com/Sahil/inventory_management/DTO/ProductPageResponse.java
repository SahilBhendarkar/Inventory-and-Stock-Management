package com.Sahil.inventory_management.DTO;

import java.util.List;

public class ProductPageResponse {
    private List<ProductDTO> products;
    private Long totalCount;

    public ProductPageResponse(List<ProductDTO> products, Long totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}

