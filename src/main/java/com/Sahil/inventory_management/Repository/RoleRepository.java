package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
