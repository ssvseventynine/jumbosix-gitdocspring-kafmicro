package com.sidhant.jumbosix.inventory.repository;

import com.sidhant.jumbosix.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Custom query method to look up stock by SKU name later on
    java.util.Optional<Inventory> findByProductSku(String productSku);
}