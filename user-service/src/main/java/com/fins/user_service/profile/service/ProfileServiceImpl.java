package com.fins.user_service.profile.service;

import com.fins.user_service.account.model.Account;
import com.fins.user_service.account.repository.AccountRepository;
import com.fins.user_service.profile.dtos.ProfileReq;
import com.fins.user_service.profile.dtos.ProfileRes;
import com.fins.user_service.profile.dtos.UpdateProfileReq;
import com.fins.user_service.profile.mapper.ProfileMapper;
import com.fins.user_service.profile.model.Profile;
import com.fins.user_service.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AccountRepository accountRepository;

    @Override
    public ProfileRes createProfile(ProfileReq req) {
        Profile profile = profileMapper.toEntity(req);
        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        profile.setAccount(account);

        Profile saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved);
    }

    @Override
    public ProfileRes updateProfile(String id, UpdateProfileReq req) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profileMapper.updateEntityFromRequest(req, profile);

        Profile updated = profileRepository.save(profile);

        return profileMapper.toResponse(updated);
    }

    @Override
    public Page<ProfileRes> getAllProfile(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(profileMapper::toResponse);
    }

    @Override
    public Optional<ProfileRes> getProfileById(String id) {
        return profileRepository.findById(id)
                .map(profileMapper::toResponse);
    }

    @Override
    public void deleteProfile(String id) {
        profileRepository.deleteById(id);

    }

    @Override
    public Optional<ProfileRes> getProfileByAccountId(String accountId) {
        return profileRepository.findByAccountId(accountId)
                .map(profileMapper::toResponse);
    }
}
