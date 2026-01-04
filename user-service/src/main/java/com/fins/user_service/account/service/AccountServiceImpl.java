package com.fins.user_service.account.service;

import com.fins.user_service.account.dtos.AccountReq;
import com.fins.user_service.account.dtos.AccountRes;
import com.fins.user_service.account.dtos.UpdateAccountReq;
import com.fins.user_service.account.mapper.AccountMapper;
import com.fins.user_service.account.model.Account;
import com.fins.user_service.account.model.Role;
import com.fins.user_service.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountRes createAccount(AccountReq req) {
        if(emailExists(req.getEmail())) throw new IllegalArgumentException("Email already taken: " + req.getEmail());
        if(usernameExists(req.getUsername())) throw new IllegalArgumentException("Username already taken: " + req.getUsername());
        Account account = accountMapper.toEntity(req);
        account.setPassword(passwordEncoder.encode(req.getPassword()));
        Account saved = accountRepository.save(account);

        return accountMapper.toResponse(saved);
    }

    @Override
    public AccountRes registerAccount(AccountReq req) {
        if(emailExists(req.getEmail())) throw new IllegalArgumentException("Email already taken: " + req.getEmail());
        if(usernameExists(req.getUsername())) throw new IllegalArgumentException("Username already taken: " + req.getUsername());
        Account account = accountMapper.toEntity(req);
        account.setPassword(passwordEncoder.encode(req.getPassword()));
        account.setRole(Role.USER);
        Account saved = accountRepository.save(account);

        return accountMapper.toResponse(saved);
    }

    @Override
    public AccountRes updateAccount(String id, UpdateAccountReq req) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountMapper.updateEntityFromRequest(req, account);
        if(emailExists(req.getEmail())) throw new IllegalArgumentException("Email already taken: " + req.getEmail());
        if(usernameExists(req.getUsername())) throw new IllegalArgumentException("Username already taken: " + req.getUsername());
        account.setPassword(passwordEncoder.encode(req.getPassword()));
        Account updated = accountRepository.save(account);
        return accountMapper.toResponse(updated);
    }

    @Override
    public Page<AccountRes> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toResponse);
    }

    @Override
    public Optional<AccountRes> getAccountById(String id) {
        return accountRepository.findById(id)
                .map(accountMapper::toResponse);
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<AccountRes> getAccountByUsername(String username) {
        return accountRepository.findByUsername(username)
                .map(accountMapper::toResponse);
    }

    @Override
    public boolean usernameExists(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public Optional<AccountRes> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(accountMapper::toResponse);
    }

    @Override
    public boolean emailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Boolean existByAccountId(String accountId) {
        return accountRepository.existsById(accountId);
    }
}
