package com.example.paymentservice.app.service.mapper;

import com.example.paymentservice.app.service.dto.PaymentDto;
import com.example.paymentservice.persistence.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment toEntity(PaymentDto paymentDto);
}
