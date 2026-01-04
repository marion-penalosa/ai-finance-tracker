package com.fins.user_service.profile.repository;

import com.fins.user_service.profile.dtos.ProfileRes;
import com.fins.user_service.profile.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByAccountId(String accountId);
    Page<Profile> findAll(Pageable pageable);
}
