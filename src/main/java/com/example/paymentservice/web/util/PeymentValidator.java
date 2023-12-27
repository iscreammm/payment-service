package com.example.paymentservice.web.util;

import com.example.paymentservice.app.service.PaymentService;
import com.example.paymentservice.web.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;


@Component
public class PeymentValidator implements Validator {

    private final PaymentService paymentService;

    @Autowired
    public PeymentValidator(PaymentService paymentService1) {
        this.paymentService = paymentService1;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PaymentDto paymentDto = (PaymentDto) o;

        try {
            if (paymentService.checkDate(paymentDto)==false)
                errors.rejectValue("dateCard", "", "Срок действия карты истек");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
