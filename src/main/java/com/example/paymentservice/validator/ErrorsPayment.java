package com.example.paymentservice.validator;


public class ErrorsPayment {
    String message;

    public ErrorsPayment(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorStudent{" +
                ", message='" + message + '\'' +
                '}';
    }
}
