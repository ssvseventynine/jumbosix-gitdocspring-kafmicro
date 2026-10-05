package com.sidhant.jumbosix.order.controller;

import com.sidhant.jumbosix.order.model.Order;
import com.sidhant.jumbosix.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Spring automatically injects both the DB repo and KafkaTemplate beans here
    public OrderController(OrderRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        order.setStatus("PLACED");
        Order savedOrder = repository.save(order);
        
        // Formulate a clean message payload string
        String messagePayload = "OrderCreatedEvent:SKU=" + savedOrder.getProductSku() + ",Qty=" + savedOrder.getQuantity();
        
        // Asynchronously publish to the "order-events" topic
        kafkaTemplate.send("order-events", messagePayload);
        
        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}