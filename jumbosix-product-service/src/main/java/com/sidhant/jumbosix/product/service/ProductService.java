package com.sidhant.jumbosix.product.service;

import com.sidhant.jumbosix.product.model.Product;
import com.sidhant.jumbosix.product.repository.ProductRepository; // Ensure this repository import matches your project
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Product createProduct(Product product) {
        // 1. Save the metadata down permanently to product_db
        Product savedProduct = productRepository.save(product);
        
        // 2. Prepare payload event for our asynchronous subscribers
        Map<String, Object> productEvent = new HashMap<>();
        productEvent.put("sku", savedProduct.getSku());
        productEvent.put("initialStock", 0); // Sets baseline floor inventory to 0 items
        
        // 3. Emit message event down to the broker cluster 
        kafkaTemplate.send("product-creation-topic", productEvent);
        
        return savedProduct;
    }
}