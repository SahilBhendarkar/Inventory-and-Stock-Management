package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.Service.DealerUserProjection;
import com.Sahil.inventory_management.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealerRepository extends JpaRepository<Dealer, Long> {

    @Query("SELECT u.id AS id, u.name AS name " +
            "FROM Dealer d JOIN User u ON d.user.id = u.id")
    List<DealerUserProjection> getAllDealerUsers();
}
