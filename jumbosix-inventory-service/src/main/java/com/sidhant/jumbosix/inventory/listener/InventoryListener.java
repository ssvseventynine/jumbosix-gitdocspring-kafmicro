package com.sidhant.jumbosix.inventory.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

// Replace these imports with your actual package models and repository paths
import com.sidhant.jumbosix.inventory.model.Inventory;
import com.sidhant.jumbosix.inventory.repository.InventoryRepository;

@Service
public class InventoryListener {

    @Autowired
    private InventoryRepository inventoryRepository;

    @KafkaListener(topics = "product-creation-topic", groupId = "inventory-group")
    public void handleProductCreated(Map<String, Object> event) {
        if (event == null || !event.containsKey("sku")) {
            System.err.println("INVENTORY SERVICE: Received invalid or empty payload event.");
            return;
        }

        String sku = (String) event.get("sku");
        
        // Safeguard type extraction against number casting errors
        Number stockNum = (Number) event.get("initialStock");
        Integer stockValue = (stockNum != null) ? stockNum.intValue() : 0;

        // Establish matching inventory tracking entry record
        Inventory inventory = new Inventory();
        inventory.setProductSku(sku);
        inventory.setStockQuantity(stockValue); // <-- FIXED TO MATCH YOUR SETTER

        // Write directly to your inventory_db table schemas
        inventoryRepository.save(inventory);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("INVENTORY PIPELINE CALLED: New catalog SKU detected -> " + sku);
        System.out.println("Initialized baseline schema row in inventory_db successfully!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}