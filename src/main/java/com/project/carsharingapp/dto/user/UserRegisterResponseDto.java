package com.project.carsharingapp.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}