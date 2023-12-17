package com.example.paymentservice.web.controller;

import com.example.paymentservice.app.service.PaymentService;
import com.example.paymentservice.app.service.dto.PaymentDto;
import com.example.paymentservice.persistence.model.Payment;
import com.example.paymentservice.validator.ErrorsPayment;
import com.example.paymentservice.validator.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static com.example.paymentservice.validator.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /*@GetMapping()
    public void test() {
        paymentService.save(new Payment(
                UUID.randomUUID(),
                "Me",
                300,
                LocalDateTime.now(),
                PaymentStatus.PAID)
        );
    }*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Payment> getStudents() {
        return paymentService.findAll();
    }

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody @Valid PaymentDto paymentDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        paymentService.save(paymentDto);
        return ResponseEntity.ok("your order has been paid");
    }

    @ExceptionHandler
    private ResponseEntity<ErrorsPayment> handleException(Exceptions e) {
        ErrorsPayment response = new ErrorsPayment(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
