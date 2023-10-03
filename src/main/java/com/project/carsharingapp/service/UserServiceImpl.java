package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.user.UserRegistrationRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.RegistrationException;
import com.project.carsharingapp.mapper.UserMapper;
import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.RoleName;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("The email: " + registrationRequestDto.getEmail()
                    + " is already in use. Please choose a different one.");
        }
        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));
        Role userRole = roleService.getRoleByRoleName(RoleName.CUSTOMER);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        return userMapper.toDto(userRepository.save(user));
    }
}

