package com.fins.user_service.profile.dtos;

import com.fins.user_service.account.dtos.AccountRes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRes {
    private String id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;

    private String profileImgUrl;

    private String district;
    private String city;
    private String province;
    private String zipCode;
    private String country;

    private AccountRes account;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
