package com.project.carsharingapp.services;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserDto;

public interface UserService {
    UserDto findById(Long id);

    UserDto updateUserProfile(Long id, UpdateUserProfileRequestDto requestDto);

    UserDto updateUserRole(Long id, UpdateUserRoleRequestDto requestDto);
}
