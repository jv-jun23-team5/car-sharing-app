package com.project.carsharingapp.service;

import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.RoleName;

public interface RoleService {
    Role getRoleByRoleName(RoleName roleName);
}
