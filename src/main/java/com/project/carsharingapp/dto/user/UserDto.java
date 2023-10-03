package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.model.Role;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long telegramChatId;
    private Set<Role> roles;
}
