package com.fins.transaction_service.tran_account.service;

import com.fins.transaction_service.tran_account.dtos.TranAccountReq;
import com.fins.transaction_service.tran_account.dtos.TranAccountRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TranAccountService {
    TranAccountRes createTranAccount(TranAccountReq tranAccountReq);
    Page<TranAccountRes> getAllTranAccount(Pageable pageable);
    Optional<TranAccountRes> getTranAccountById(String id);

    TranAccountRes updateBalance(String tranAccId, TranAccountReq req);

    Boolean existsByTranAccountId(String tranAccountId);

}
