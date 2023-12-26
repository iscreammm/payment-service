package com.example.paymentservice.web.controller;

import com.example.paymentservice.app.enums.PaymentStatus;
import com.example.paymentservice.app.service.PaymentService;
import com.example.paymentservice.web.controller.exception.BindingException;
import com.example.paymentservice.web.dto.PaymentDto;
import com.example.paymentservice.web.dto.ResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

class PaymentControllerTest {

    private PaymentService paymentService;

    private BindingResult bindingResult;

    private PaymentController paymentController;

    @BeforeEach
    void init() {
        paymentService = Mockito.mock(PaymentService.class);
        bindingResult = Mockito.mock(BindingResult.class);

        paymentController = new PaymentController(paymentService);
    }

    @Test
    void checkPayment() {
        Long id = 2L;

        ResponseDto response = new ResponseDto(PaymentStatus.NOT_PAID, id);

        Mockito.when(paymentService.checkPayment(id)).thenReturn(response);

        ResponseEntity<ResponseDto> realResponse = paymentController.checkPayment(id);

        Assertions.assertSame(HttpStatus.OK, realResponse.getStatusCode());
        Assertions.assertEquals(response, realResponse.getBody());
    }

    @Test
    void paymentSuccessTest() {
        PaymentDto paymentDto = new PaymentDto(
                2L,
                "",
                "",
                "",
                "",
                0);

        ResponseDto response = new ResponseDto(
                PaymentStatus.PAID,
                paymentDto.getOrderId(),
                LocalDateTime.now().toString(),
                paymentDto.getTotalPrice()
        );

        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(paymentService.doPayment(paymentDto)).thenReturn(response);

        ResponseEntity<ResponseDto> realResponse = paymentController.payment(paymentDto, bindingResult);

        Assertions.assertSame(HttpStatus.OK, realResponse.getStatusCode());
        Assertions.assertEquals(response, realResponse.getBody());
    }

    @Test
    void paymentWithBindingErrors() {
        PaymentDto paymentDto = new PaymentDto(
                2L,
                "",
                "",
                "",
                "",
                0);

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        Assertions.assertThrows(BindingException.class, () -> paymentController.payment(paymentDto, bindingResult));
    }
}