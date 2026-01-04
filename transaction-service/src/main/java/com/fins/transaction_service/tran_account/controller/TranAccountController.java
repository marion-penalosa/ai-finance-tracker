package com.fins.transaction_service.tran_account.controller;

import com.fins.transaction_service.tran_account.dtos.TranAccountReq;
import com.fins.transaction_service.tran_account.dtos.TranAccountRes;
import com.fins.transaction_service.tran_account.service.TranAccountService;
import com.fins.transaction_service.transaction.dtos.TransactionReq;
import com.fins.transaction_service.transaction.dtos.TransactionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tranAccounts")
@RequiredArgsConstructor
public class TranAccountController {
    private final TranAccountService tranAccountService;

    @PostMapping
    public ResponseEntity<TranAccountRes> createTranAccount(@RequestBody TranAccountReq request){
        TranAccountRes response = tranAccountService.createTranAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TranAccountRes>> getAllTranAccounts(Pageable pageable) {
        return ResponseEntity.ok(tranAccountService.getAllTranAccount(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranAccountRes> getTranAccountById(@PathVariable String id) {
        return tranAccountService.getTranAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
