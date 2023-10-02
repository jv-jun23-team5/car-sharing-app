package com.project.carsharingapp.dto.car;

import com.project.carsharingapp.model.Car;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDto {
    private Long id;
    private String model;
    private String brand;
    private Integer inventory;
    private Car.CarType carType;
    private BigDecimal dailyFee;
}
