package com.example.paymentservice.app.service;

import com.example.paymentservice.app.enums.PaymentStatus;
import com.example.paymentservice.persistence.model.Payment;
import com.example.paymentservice.persistence.repository.PaymentRepository;
import com.example.paymentservice.web.dto.PaymentDto;
import com.example.paymentservice.web.dto.ResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
        Payment payment = new Payment(2L, "", BigDecimal.ONE, LocalDateTime.now());
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
        Payment payment = new Payment(id, "", BigDecimal.ONE, LocalDateTime.now());

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
        Payment payment = new Payment(id, "", BigDecimal.ONE, LocalDateTime.now());

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
        Payment payment = new Payment(2L, "", BigDecimal.ONE, LocalDateTime.now());
        PaymentDto paymentDto = new PaymentDto(
                payment.getOrderId(),
                "",
                payment.getCustomerName(),
                "",
                "",
                payment.getPaymentAmount()
        );

        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        ResponseDto generated = paymentService.doPayment(paymentDto);

        Assertions.assertEquals(payment.getOrderId(), generated.getOrderId());
    }

    @Test
    void checkDateFalse() throws ParseException {
        PaymentDto paymentDto = new PaymentDto(
                2L,
                "",
                "",
                "11/23",
                "",
                BigDecimal.ONE
        );

        assertFalse(paymentService.checkDate(paymentDto));
    }

    @Test
    void checkDateTrue() throws ParseException {
        PaymentDto paymentDto = new PaymentDto(
                2L,
                "",
                "",
                "11/26",
                "",
                BigDecimal.ONE
        );

        assertTrue(paymentService.checkDate(paymentDto));
    }

    @Test
    void checkDateException() {
        PaymentDto paymentDto = new PaymentDto(
                2L,
                "",
                "",
                "abcd",
                "",
                BigDecimal.ONE
        );

        assertThrows(ParseException.class, () -> paymentService.checkDate(paymentDto));
    }
}
