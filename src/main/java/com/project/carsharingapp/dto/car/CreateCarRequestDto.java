package com.project.carsharingapp.dto.car;

import com.project.carsharingapp.model.Car;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCarRequestDto {
    @NotNull
    @Length(min = 1, max = 255)
    private String model;
    @NotNull
    @Length(min = 1, max = 255)
    private String brand;
    @NotNull
    @Min(0)
    private Integer inventory;
    @NotNull
    private Car.CarType carType;
    @NotNull
    @Min(0)
    private BigDecimal dailyFee;
}
