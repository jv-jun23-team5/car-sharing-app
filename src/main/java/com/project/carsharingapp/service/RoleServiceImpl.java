package com.project.carsharingapp.service;

import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.repository.RoleRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByRoleName(Role.RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Can't find role: " + roleName));
    }
}
