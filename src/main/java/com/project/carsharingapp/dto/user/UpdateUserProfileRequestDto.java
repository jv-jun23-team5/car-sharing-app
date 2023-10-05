package com.project.carsharingapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateUserProfileRequestDto {
    @Schema(example = "user@gmail.com")
    @NotBlank
    @Length(min = 4, max = 50)
    private String email;
    @Schema(example = "Ivan")
    @NotBlank
    @Length(min = 1, max = 50)
    private String firstName;
    @Schema(example = "Ivanov")
    @NotBlank
    @Length(min = 1, max = 50)
    private String lastName;
    @Schema(example = "easypass1234")
    @NotBlank
    @Length(min = 6, max = 100)
    private String password;
}
