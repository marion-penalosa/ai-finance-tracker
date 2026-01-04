package com.fins.user_service.account.repository;

import com.fins.user_service.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
    Page<Account> findAll(Pageable pageable);
}
