package com.fins.user_service.account.controller;

import com.fins.user_service.account.dtos.AccountReq;
import com.fins.user_service.account.dtos.AccountRes;
import com.fins.user_service.account.dtos.UpdateAccountReq;
import com.fins.user_service.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountRes> createAccount(@Valid @RequestBody AccountReq request){
        AccountRes response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountRes> updateAccount(
            @PathVariable String id,
            @Valid @RequestBody UpdateAccountReq request
    ){
        AccountRes response = accountService.updateAccount(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AccountRes>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRes> getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{accountId}/validate")
    public ResponseEntity<Boolean> validateAccount(@PathVariable String accountId){
        return ResponseEntity.ok(accountService.existByAccountId(accountId));
    }
}
