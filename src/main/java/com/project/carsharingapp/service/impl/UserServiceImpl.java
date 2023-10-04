package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.exception.RegistrationException;
import com.project.carsharingapp.mapper.UserMapper;
import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.RoleRepository;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.service.RoleService;
import com.project.carsharingapp.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserResponseDto getById(Long id) {
        User user = getCurrentUser(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUserProfile(Long id, UpdateUserProfileRequestDto requestDto) {
        User user = getCurrentUser(id);
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setPassword(requestDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateUserRole(Long id, UpdateUserRoleRequestDto requestDto) {
        User user = getCurrentUser(id);
        Optional<Role> roleName = roleRepository.findRoleByRoleName(requestDto.getRole());
        user.setRoles(Set.of(roleName.orElseThrow(
                () -> new RuntimeException("Can't find role " + requestDto.getRole()))));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("The email: " + registrationRequestDto.getEmail()
                    + " is already in use. Please choose a different one.");
        }
        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));
        Role userRole = roleService.getRoleByRoleName(Role.RoleName.ROLE_CUSTOMER);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Can't find a user by email: " + email)
        );
    }

    private User getCurrentUser(Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + id));
    }
}
