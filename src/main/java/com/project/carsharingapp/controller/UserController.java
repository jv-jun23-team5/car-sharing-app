package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.user.UpdateUserProfileRequestDto;
import com.project.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import com.project.carsharingapp.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management",
        description = "Endpoints for managing users")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user profile info by user id",
            description = "Get user's detailed information about"
                    + " user profile by user identification number")
    public UserDto getById(@PathVariable Long id) {
        return null;
    }

    @PatchMapping("/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user role by user id",
            description = "Update user role by user identification number")
    public UserDto updateUserRole(@PathVariable Long id,
                                     @RequestBody @Valid
                                     UpdateUserRoleRequestDto requestDto) {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user info by id",
            description = "Update the user profile information "
                    + "by user identification number")
    public UserDto updateUserProfile(@PathVariable Long id,
                          @Valid @RequestBody UpdateUserProfileRequestDto requestDto) {
        return null;
    }
}
