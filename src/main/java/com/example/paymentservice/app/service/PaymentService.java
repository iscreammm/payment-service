package com.example.paymentservice.app.service;

import com.example.paymentservice.app.service.dto.PaymentDto;
import com.example.paymentservice.app.service.mapper.PaymentMapper;
import com.example.paymentservice.persistence.model.Payment;
import com.example.paymentservice.persistence.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findById(Long uuid) throws SQLException {
        return paymentRepository.findById(uuid).orElseThrow(SQLException::new);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void deleteById(Long uuid) {
        paymentRepository.deleteById(uuid);
    }

    @Transactional
    public Payment save(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.INSTANCE.toEntity(paymentDto);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}
