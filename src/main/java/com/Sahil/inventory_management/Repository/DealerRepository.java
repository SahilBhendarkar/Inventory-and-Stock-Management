package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
