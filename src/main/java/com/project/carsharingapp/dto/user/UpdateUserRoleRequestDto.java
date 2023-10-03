package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.model.RoleName;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDto {
    @NotNull
    private RoleName role;
}
