package com.sidhant.jumbosix.inventory.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    // The @KafkaListener annotation tells Spring to continuously listen to the "order-events" topic
    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consumeOrderEvent(String message) {
        System.out.println("=================================================");
        System.out.println("INVENTORY SERVICE CONSUMED EVENT PAYLOAD: " + message);
        System.out.println("=================================================");
        
        // This is exactly where your enterprise database logic will go, such as:
        // inventoryRepository.findByProductSku(extractedSku)... decrement stock quantity...
    }
}