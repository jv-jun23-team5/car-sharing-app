package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDto {
    @Schema(example = "ROLE_CUSTOMER | ROLE_MANAGER")
    @NotNull
    private Role.RoleName role;
}
