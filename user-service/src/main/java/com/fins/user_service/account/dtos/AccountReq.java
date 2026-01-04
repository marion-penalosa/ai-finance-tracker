package com.fins.user_service.account.dtos;

import com.fins.user_service.account.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountReq {
    @NotNull(message = "Username is required")
    @Size(min = 8, message = "Username must be at least 8 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Inputted value must be in a valid email format")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min =8, message = "Password must be at least 8 characters")
    private String password;

    private Role role;
}
