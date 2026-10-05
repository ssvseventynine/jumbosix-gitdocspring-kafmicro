package com.sidhant.jumbosix.inventory.controller;

import com.sidhant.jumbosix.inventory.model.Inventory;
import com.sidhant.jumbosix.inventory.repository.InventoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository repository;

    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Inventory addStock(@RequestBody Inventory inventory) {
        return repository.save(inventory);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }
    
    @GetMapping("/{sku}")
    public Inventory getStockBySku(@PathVariable String sku) {
        return repository.findByProductSku(sku)
                .orElseThrow(() -> new RuntimeException("SKU not found: " + sku));
    }
}