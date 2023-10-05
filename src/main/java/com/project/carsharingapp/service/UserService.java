package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserRegisterResponseDto;
import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.RegistrationException;
import com.project.carsharingapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegisterResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException;

    UserResponseDto updateUserProfile(Long id, UpdateUserProfileRequestDto requestDto);

    UserResponseDto updateUserRole(Long id, UpdateUserRoleRequestDto requestDto);

    UserResponseDto getById(Long id);

    User getByEmail(String email);

    User getByAuthentication(Authentication auth);

    User getUserByRentalId(Long rentalId);
}
