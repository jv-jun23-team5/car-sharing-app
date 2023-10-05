package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.service.validate.PasswordValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @Schema(example = "user@gmail.com")
    @NotEmpty
    @Size(min = 8, max = 20)
    @Email
    private String email;
    @Schema(example = "easypass1234")
    @PasswordValidator
    private String password;
}
