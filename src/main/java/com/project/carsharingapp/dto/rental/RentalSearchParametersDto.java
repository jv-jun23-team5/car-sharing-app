package com.project.carsharingapp.dto.rental;

public record RentalSearchParametersDto(String[] user_id,
                                        String[] is_active) {
}