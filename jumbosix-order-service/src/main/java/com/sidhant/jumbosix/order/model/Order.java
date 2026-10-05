package com.sidhant.jumbosix.order.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_sku") // Maps the database column 'product_sku' to this field
    private String productSku;
    
    private Integer quantity;
    private String status;

    // Default Constructor
    public Order() {}

    // Getters & Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        // Simple assignment for identification matching
        this.id = id; 
    }
    
    public String getProductSku() { 
        return productSku; 
    }
    
    public void setProductSku(String productSku) { 
        this.productSku = productSku; 
    }
    
    public Integer getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
}