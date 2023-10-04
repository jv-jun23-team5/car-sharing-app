package com.project.carsharingapp.service;

import com.project.carsharingapp.model.Role;

public interface RoleService {
    Role getRoleByRoleName(Role.RoleName roleName);
}
