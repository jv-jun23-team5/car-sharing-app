package com.project.carsharingapp.mapper;

import com.project.carsharingapp.config.MapperConfig;
import com.project.carsharingapp.dto.user.UserDto;
import com.project.carsharingapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);
}
