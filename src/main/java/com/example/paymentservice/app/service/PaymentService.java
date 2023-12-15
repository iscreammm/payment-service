package com.example.paymentservice.app.service;

import com.example.paymentservice.persistence.model.Payment;
import com.example.paymentservice.persistence.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findById(UUID uuid) throws SQLException {
        return paymentRepository.findById(uuid).orElseThrow(SQLException::new);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void deleteById(UUID uuid) {
        paymentRepository.deleteById(uuid);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
