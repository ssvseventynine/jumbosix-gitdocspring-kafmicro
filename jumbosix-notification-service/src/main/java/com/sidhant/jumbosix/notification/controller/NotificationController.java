package com.sidhant.jumbosix.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @GetMapping("/status")
    public String getStatus() {
        return "Notification Service Engine is active and awaiting incoming message streams...";
    }
}