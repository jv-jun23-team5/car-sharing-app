package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserRegisterResponseDto;
import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.RegistrationException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegisterResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException;

    UserResponseDto findById(Long id);

    UserResponseDto updateUserProfile(Long id, UpdateUserProfileRequestDto requestDto);

    UserResponseDto updateUserRole(Long id, UpdateUserRoleRequestDto requestDto);
}
