package com.project.carsharingapp.dto.rental;

public record RentalSearchParametersDto(
        String[] userId,
        String[] isActive
) {
}

