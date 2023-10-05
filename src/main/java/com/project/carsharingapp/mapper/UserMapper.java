package com.project.carsharingapp.mapper;

import com.project.carsharingapp.config.MapperConfig;
import com.project.carsharingapp.dto.user.UserRegisterResponseDto;
import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    UserRegisterResponseDto toRegistrationDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}

