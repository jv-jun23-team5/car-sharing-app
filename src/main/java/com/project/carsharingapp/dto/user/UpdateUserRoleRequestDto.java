package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.model.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDto {
    @NotNull
    private Role.RoleName role;
}
