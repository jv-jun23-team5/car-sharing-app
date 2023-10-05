package com.project.carsharingapp.dto.user;

import com.project.carsharingapp.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    @Schema(example = "user@gmail.com")
    private String email;
    @Schema(example = "Ivan")
    private String firstName;
    @Schema(example = "Ivanov")
    private String lastName;
    @Schema(example = "telegram_id")
    private Long telegramChatId;
    private Set<Role> roles;
}
