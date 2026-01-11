package com.fins.user_service.profile.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileReq {
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String profileImgUrl;
    private String district;
    private String city;
    private String province;
    private String zipCode;
    private String country;
    private String accountId;
}
