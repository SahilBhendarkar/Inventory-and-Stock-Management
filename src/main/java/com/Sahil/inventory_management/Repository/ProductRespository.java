package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRespository extends JpaRepository<Product, Long> {


}
