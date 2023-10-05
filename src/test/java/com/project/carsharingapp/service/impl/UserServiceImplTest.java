package com.project.carsharingapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserResponseDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.UserMapper;
import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.RoleRepository;
import com.project.carsharingapp.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static User testUser;
    private static UserResponseDto testUserResponseDto;
    private static UpdateUserProfileRequestDto updateUserProfileRequestDto;
    private static UpdateUserRoleRequestDto updateUserRoleRequestDto;
    private static final Long TEST_ID = 1L;
    private static final Long NON_EXIST_ID = 2L;
    private static final String TEST_EMAIL = "test@email.com";
    private static final Role ROLE_MANAGER = new Role();
    private static final Role ROLE_CUSTOMER = new Role();
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void setUp() {
        ROLE_MANAGER.setId(1L);
        ROLE_MANAGER.setRoleName(Role.RoleName.ROLE_MANAGER);
        ROLE_CUSTOMER.setId(2L);
        ROLE_CUSTOMER.setRoleName(Role.RoleName.ROLE_CUSTOMER);

        testUser = new User();
        testUser.setId(TEST_ID);
        testUser.setEmail("test@email.com");
        testUser.setFirstName("test_FirstName");
        testUser.setLastName("test_LastName");
        testUser.setRoles(Set.of(ROLE_MANAGER));

        testUserResponseDto = new UserResponseDto();
        testUserResponseDto.setId(testUser.getId());
        testUserResponseDto.setEmail(testUser.getEmail());
        testUserResponseDto.setFirstName(testUser.getFirstName());
        testUserResponseDto.setLastName(testUser.getLastName());
        testUserResponseDto.setRoles(Set.of(ROLE_MANAGER));

        updateUserProfileRequestDto = new UpdateUserProfileRequestDto();
        updateUserProfileRequestDto.setEmail("new-email@example.com");
        updateUserProfileRequestDto.setFirstName("NewFirstName");
        updateUserProfileRequestDto.setLastName("NewLastName");
        updateUserProfileRequestDto.setPassword("admin2023admin");

        updateUserRoleRequestDto = new UpdateUserRoleRequestDto();
        updateUserRoleRequestDto.setRole(Role.RoleName.ROLE_CUSTOMER);
    }

    @Test
    @DisplayName("Get user by id - user exist")
    public void getById_UserExist_ReturnUserResponseDto() {
        when(userRepository.findUserById(TEST_ID)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserResponseDto);
        UserResponseDto actual = userService.getById(TEST_ID);
        assertNotNull(actual);
        assertEquals(testUserResponseDto, actual);
    }

    @Test
    @DisplayName("Get user by id - user not found")
    public void getById_UserNotFound_ThrowEntityNotFoundException() {
        when(userRepository.findUserById(NON_EXIST_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getById(NON_EXIST_ID));
    }

    @Test
    @DisplayName("Update user profile - user exist")
    public void update_UserExist_ReturnUpdatedUserResponseDto() {
        when(userRepository.findUserById(TEST_ID)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(any(User.class))).thenReturn(testUserResponseDto);
        when(passwordEncoder.encode("admin2023admin")).thenReturn("admin2023admin");
        when(userRepository.save(any())).thenReturn(testUser);

        UserResponseDto actual = userService.updateUserProfile(TEST_ID,
                updateUserProfileRequestDto);

        assertNotNull(actual);
        assertEquals(testUserResponseDto, actual);
    }

    @Test
    @DisplayName("Get user by email - user exist")
    public void getByEmail_UserExist_ReturnUser() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        User actual = userService.getByEmail(TEST_EMAIL);
        assertEquals(testUser, actual);
    }

    @Test
    @DisplayName("Update user role - user exist")
    public void update_UserRole_ReturnUpdatedUserResponseDto() {
        when(userRepository.findUserById(TEST_ID)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(any(User.class))).thenReturn(testUserResponseDto);
        when(userRepository.save(any())).thenReturn(testUser);
        when(roleRepository.findRoleByRoleName(Role.RoleName.ROLE_CUSTOMER))
                .thenReturn(Optional.of(ROLE_CUSTOMER));

        UserResponseDto actual = userService.updateUserRole(TEST_ID, updateUserRoleRequestDto);

        assertEquals(testUserResponseDto, actual);
    }
}
