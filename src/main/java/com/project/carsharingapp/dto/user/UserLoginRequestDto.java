package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.service.validate.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotEmpty
    @Size(min = 8, max = 20)
    @Email
    private String email;
    @PasswordValidator
    private String password;
}
