package com.fins.user_service.profile.service;

import com.fins.user_service.profile.dtos.ProfileReq;
import com.fins.user_service.profile.dtos.ProfileRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProfileService {

    ProfileRes createProfile(ProfileReq req);
    ProfileRes updateProfile(ProfileReq req);
    Page<ProfileRes> getAllProfile(Pageable pageable);
    Optional<ProfileRes> getProfileById(String id);
    void deleteProfile(String id);

    Optional<ProfileRes> getProfileByAccountId(String accountId);
}
