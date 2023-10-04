package com.project.carsharingapp.mapper;

import com.project.carsharingapp.config.MapperConfig;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(source = "rentalId", target = "payment.rental.id")
    PaymentResponseDto toDto(Payment payment);
}
