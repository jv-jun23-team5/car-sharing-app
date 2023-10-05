package com.project.carsharingapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterResponseDto {
    private Long id;
    @Schema(example = "user@gmail.com")
    private String email;
    @Schema(example = "Ivan")
    private String firstName;
    @Schema(example = "Ivanov")
    private String lastName;
}
