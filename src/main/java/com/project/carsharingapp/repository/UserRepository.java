package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @Query("FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findUserById(Long id);

    User findByTelegramChatId(Long chatId);

    @Query("FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    List<User> findByRoles(Set<Role> roles);
}
