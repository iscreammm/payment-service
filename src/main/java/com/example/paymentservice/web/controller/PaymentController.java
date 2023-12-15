package com.example.paymentservice.web.controller;

import com.example.paymentservice.app.enums.PaymentStatus;
import com.example.paymentservice.app.service.PaymentService;
import com.example.paymentservice.persistence.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/get")
    public void test() {
        paymentService.save(new Payment(
                UUID.randomUUID(),
                "Me",
                300,
                LocalDateTime.now(),
                PaymentStatus.PAID)
        );
    }
}
