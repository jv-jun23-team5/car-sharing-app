package com.project.carsharingapp.dto.user;

import jakarta.validation.constraints.NotEmpty;

public class UserRegistrationRequestDto {

    private String email;
    @NotEmpty
    private String password;
    private String repeatPassword;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String shippingAddress;
}
