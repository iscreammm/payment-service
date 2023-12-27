package com.example.paymentservice.web.controller;

import com.example.paymentservice.app.service.PaymentService;
import com.example.paymentservice.web.dto.PaymentDto;
import com.example.paymentservice.web.dto.ResponseDto;
import com.example.paymentservice.web.util.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static com.example.paymentservice.web.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping(
        path = "/payment",
        consumes = "application/json",
        produces = "application/json"
)
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentValidator paymentValidator;


    @Autowired
    public PaymentController(PaymentService paymentService, PaymentValidator paymentValidator) {
        this.paymentService = paymentService;
        this.paymentValidator = paymentValidator;
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<ResponseDto> checkPayment(@PathVariable Long orderId) {
        ResponseDto response = paymentService.checkPayment(orderId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> payment(@RequestBody @Valid PaymentDto paymentDto, BindingResult bindingResult) {
        paymentValidator.validate(paymentDto, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        ResponseDto response = paymentService.doPayment(paymentDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
