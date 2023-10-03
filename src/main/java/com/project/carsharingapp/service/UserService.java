package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.RegistrationException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException;

}
