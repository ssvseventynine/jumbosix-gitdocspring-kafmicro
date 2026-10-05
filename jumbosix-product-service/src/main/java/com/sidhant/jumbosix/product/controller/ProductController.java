package com.sidhant.jumbosix.product.controller;

import com.sidhant.jumbosix.product.model.Product;
import com.sidhant.jumbosix.product.repository.ProductRepository;
import com.sidhant.jumbosix.product.service.ProductService; // Imported the service layer
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductRepository repository;
    private final ProductService productService; // Declared our new service bean

    // Updated constructor to inject both the repository and the service layer
    public ProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        // Redirected call to our service layer to execute the Kafka broadcast pipeline!
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}