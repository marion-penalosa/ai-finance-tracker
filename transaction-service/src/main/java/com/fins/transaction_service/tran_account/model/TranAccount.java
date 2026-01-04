package com.fins.transaction_service.tran_account.model;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "tran_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranAccount {
    @Id
    private String id;
    private String accountId; //connected in user service
    private AccountType type;
    private BigDecimal balance = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
