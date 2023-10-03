package com.project.carsharingapp.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateUserProfileRequestDto {
    @NotBlank
    @Length(min = 4, max = 50)
    private String email;
    @NotBlank
    @Length(min = 1, max = 50)
    private String firstName;
    @NotBlank
    @Length(min = 1, max = 50)
    private String lastName;
    @NotBlank
    @Length(min = 6, max = 100)
    private String password;
}
