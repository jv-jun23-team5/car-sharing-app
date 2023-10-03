package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName roleName);
}
