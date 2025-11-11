package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
