package com.example.paymentservice.app.service.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PaymentDto {
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String customerName;

    @NotEmpty
    @Pattern(regexp="\\d{16}", message = "Номер карты должено состоять из 16 цифр")
    private String cardNumber;

    @NotEmpty
    @Pattern(regexp="\\d{3}", message = "CVV код должен состоять из 3 цифр")
    private String CVV;

    private Integer paymentAmount;

    public PaymentDto(Long id, String customerName, Integer paymentAmount) {
        this.id = id;
        this.customerName = customerName;
        this.paymentAmount = paymentAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}