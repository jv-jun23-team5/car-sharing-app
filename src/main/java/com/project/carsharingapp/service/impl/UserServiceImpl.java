package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.UserMapper;
import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.RoleRepository;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.service.UserService;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserDto findById(Long id) {
        User user = getCurrentUser(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUserProfile(Long id, UpdateUserProfileRequestDto requestDto) {
        User user = getCurrentUser(id);
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setPassword(requestDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUserRole(Long id, UpdateUserRoleRequestDto requestDto) {
        User user = getCurrentUser(id);
        Optional<Role> roleName = roleRepository.findByRoleName(requestDto.getRole());
        user.setRoles(Set.of(roleName.orElseThrow(
                () -> new RuntimeException("Can't find role " + requestDto.getRole()))));
        return userMapper.toDto(userRepository.save(user));
    }

    private User getCurrentUser(Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + id));
    }
}
