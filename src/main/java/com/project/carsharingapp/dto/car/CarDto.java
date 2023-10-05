package com.project.carsharingapp.dto.car;

import com.project.carsharingapp.model.Car;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDto {
    private Long id;
    @Schema(example = "Q7")
    private String model;
    @Schema(example = "Audi")
    private String brand;
    private Integer inventory;
    @Schema(example = "HATCHBACK")
    private Car.CarType carType;
    @Schema(example = "50.0")
    private BigDecimal dailyFee;
}
