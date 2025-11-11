package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
