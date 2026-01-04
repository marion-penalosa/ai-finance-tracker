package com.fins.transaction_service.tran_account.dtos;

import com.fins.transaction_service.tran_account.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranAccountReq {
    private String accountId;
    private AccountType type;
    private BigDecimal balance;
}
