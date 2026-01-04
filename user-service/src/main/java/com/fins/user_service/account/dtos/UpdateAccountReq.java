package com.fins.user_service.account.dtos;

import com.fins.user_service.account.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountReq {
    private String username;
    private String email;
    private String password;
    private Role role;
}
