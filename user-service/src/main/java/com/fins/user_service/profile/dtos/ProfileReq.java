package com.fins.user_service.profile.dtos;

import jakarta.persistence.Column;
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
public class ProfileReq {

    @NotNull(message = "First name is required")
    private String firstname;

    @NotNull(message = "Last name is required")
    private String lastname;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be a valid date")
    private LocalDate dateOfBirth;

    private String profileImgUrl;

    private String district;
    private String city;
    private String province;
    private String zipCode;
    private String country;

    private String accountId;
}
