package com.fins.user_service.profile.controller;

import com.fins.user_service.profile.dtos.ProfileReq;
import com.fins.user_service.profile.dtos.ProfileRes;
import com.fins.user_service.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileRes> createProfile(@Valid @RequestBody ProfileReq req){
        ProfileRes res = profileService.createProfile(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    //update profile info only
    //update profile pic

    @GetMapping
    public ResponseEntity<Page<ProfileRes>> getAllProfiles(Pageable pageable){
        return ResponseEntity.ok(profileService.getAllProfile(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileRes> getProfileById(@PathVariable String id){
        return profileService.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ProfileRes> getProfileByAccountId(@PathVariable String accountId){
        return profileService.getProfileByAccountId(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String id){
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
