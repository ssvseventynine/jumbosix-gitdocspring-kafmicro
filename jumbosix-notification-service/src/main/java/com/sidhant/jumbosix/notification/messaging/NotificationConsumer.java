package com.sidhant.jumbosix.notification.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void listenToOrderEvents(String message) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("NOTIFICATION SERVICE CAUGHT BROADCAST EVENT!");
        System.out.println("TRIGGERING SMS/EMAIL OUTBOUND: Sending receipt alert -> " + message);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}