package com.example.paymentservice.app.service;

import com.example.paymentservice.app.enums.PaymentStatus;
import com.example.paymentservice.persistence.model.Payment;
import com.example.paymentservice.persistence.repository.PaymentRepository;
import com.example.paymentservice.web.dto.PaymentDto;
import com.example.paymentservice.web.dto.ResponseDto;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

class PaymentServiceTest {

    private PaymentRepository paymentRepository;

    private PaymentService paymentService;

    @BeforeEach
    void init() {
        paymentRepository = Mockito.mock(PaymentRepository.class);

        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void save() {
        Payment payment = new Payment(2L, "", 2, LocalDateTime.now());
        PaymentDto paymentDto = new PaymentDto(
                payment.getOrderId(),
                "",
                payment.getCustomerName(),
                "",
                "",
                payment.getPaymentAmount());

        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment saved = paymentService.save(paymentDto);

        Assertions.assertEquals(payment.getOrderId(), saved.getOrderId());
    }

    @Test
    void checkPaymentPresent() {
        Long id = 2L;
        Payment payment = new Payment(id, "", 2, LocalDateTime.now());

        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        ResponseDto response = new ResponseDto(
                PaymentStatus.PAID,
                payment.getOrderId(),
                payment.getPaymentDate().toString(),
                payment.getPaymentAmount()
        );

        ResponseDto generated = paymentService.checkPayment(id);

        Assertions.assertEquals(response.getStatus(), generated.getStatus());
        Assertions.assertEquals(response.getOrderId(), generated.getOrderId());
        Assertions.assertEquals(response.getPrice(), generated.getPrice());
    }

    @Test
    void checkPaymentEmpty() {
        Long id = 2L;
        Payment payment = new Payment(id, "", 2, LocalDateTime.now());

        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseDto response = new ResponseDto(
                PaymentStatus.NOT_PAID,
                payment.getOrderId(),
                null,
                null
        );

        ResponseDto generated = paymentService.checkPayment(id);

        Assertions.assertEquals(response.getStatus(), generated.getStatus());
        Assertions.assertEquals(response.getOrderId(), generated.getOrderId());
        Assertions.assertEquals(response.getDate(), generated.getDate());
        Assertions.assertEquals(response.getPrice(), generated.getPrice());
    }

    @Test
    void doPayment() {
        Payment payment = new Payment(2L, "", 2, LocalDateTime.now());
        PaymentDto paymentDto = new PaymentDto(
                payment.getOrderId(),
                "",
                payment.getCustomerName(),
                "",
                "",
                payment.getPaymentAmount());

        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        ResponseDto generated = paymentService.doPayment(paymentDto);

        Assertions.assertEquals(payment.getOrderId(), generated.getOrderId());
    }
}