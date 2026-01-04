package com.fins.transaction_service.tran_account.service;

import com.fins.transaction_service.tran_account.dtos.TranAccountReq;
import com.fins.transaction_service.tran_account.dtos.TranAccountRes;
import com.fins.transaction_service.tran_account.mapper.TranAccountMapper;
import com.fins.transaction_service.tran_account.model.TranAccount;
import com.fins.transaction_service.tran_account.repository.TranAccountRepository;
import com.fins.transaction_service.transaction.mapper.TransactionMapper;
import com.fins.transaction_service.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransAccountServiceImpl implements TranAccountService{

    private final TranAccountRepository tranAccountRepository;
    private final TranAccountMapper tranAccountMapper;
    private final AccountValidationService accountValidationService;

    @Override
    public TranAccountRes createTranAccount(TranAccountReq tranAccountReq) {
        boolean isValidAccount = accountValidationService.validateAccount(tranAccountReq.getAccountId());

        if(!isValidAccount){
            throw  new RuntimeException("Invalid Account: " + tranAccountReq.getAccountId());
        }

        TranAccount tranAccount = tranAccountMapper.toEntity(tranAccountReq);

        if (tranAccountReq.getBalance() == null){
            tranAccount.setBalance(BigDecimal.ZERO);
        }

        TranAccount saved = tranAccountRepository.save(tranAccount);
        return tranAccountMapper.toResponse(saved);
    }

    @Override
    public Page<TranAccountRes> getAllTranAccount(Pageable pageable) {
        return tranAccountRepository.findAll(pageable)
                .map(tranAccountMapper::toResponse);
    }

    @Override
    public Optional<TranAccountRes> getTranAccountById(String id) {
        return tranAccountRepository.findById(id)
                .map(tranAccountMapper::toResponse);
    }

    @Override
    public TranAccountRes updateBalance(String tranAccId, TranAccountReq req) {
        TranAccount tranAccount = tranAccountRepository.findById(tranAccId)
                .orElseThrow(() -> new RuntimeException("Transaction Account not found: " + tranAccId));

        tranAccountMapper.updateEntityFromRequest(req, tranAccount);
        TranAccount updated = tranAccountRepository.save(tranAccount);
        return tranAccountMapper.toResponse(updated);
    }

    @Override
    public Boolean existsByTranAccountId(String tranAccountId) {
        return tranAccountRepository.existsById(tranAccountId);
    }
}
