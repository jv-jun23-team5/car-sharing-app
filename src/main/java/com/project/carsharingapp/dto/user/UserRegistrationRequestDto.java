package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.service.validate.FieldMatch;
import com.project.carsharingapp.service.validate.PasswordValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords don't match!"
)
@Getter
@Setter
public class UserRegistrationRequestDto {
    @Schema(example = "user@gmail.com")
    @Email
    private String email;
    @Schema(example = "easypass1234")
    @NotEmpty
    @PasswordValidator
    private String password;
    @Schema(example = "easypass1234")
    private String repeatPassword;
    @Schema(example = "Ivan")
    @NotEmpty
    private String firstName;
    @Schema(example = "Ivanov")
    @NotEmpty
    private String lastName;
}
