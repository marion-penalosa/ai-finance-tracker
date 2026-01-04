package com.fins.user_service.account.service;

import com.fins.user_service.account.dtos.AccountReq;
import com.fins.user_service.account.dtos.AccountRes;
import com.fins.user_service.account.dtos.UpdateAccountReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    AccountRes createAccount(AccountReq req);
    AccountRes registerAccount(AccountReq req);
    AccountRes updateAccount(String id, UpdateAccountReq req);
    Page<AccountRes> getAllAccounts(Pageable pageable);
    Optional<AccountRes> getAccountById(String id);
    void deleteAccount(String id);

    Optional<AccountRes> getAccountByUsername(String username);
    boolean usernameExists(String username);

    Optional<AccountRes> getAccountByEmail(String email);
    boolean emailExists(String email);

    Boolean existByAccountId(String accountId);
}
