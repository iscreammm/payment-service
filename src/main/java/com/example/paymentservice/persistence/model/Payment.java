package com.example.paymentservice.persistence.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "db_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @Column(name = "payment_date", columnDefinition = "timestamp with time zone")
    private LocalDateTime paymentDate;


    public Payment() {
    }

    public Payment(Integer paymentAmount, LocalDateTime paymentDate) {
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

}
