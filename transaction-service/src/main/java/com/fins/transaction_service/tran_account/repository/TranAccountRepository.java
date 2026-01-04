package com.fins.transaction_service.tran_account.repository;

import com.fins.transaction_service.tran_account.model.TranAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranAccountRepository extends MongoRepository<TranAccount, String> {
    @Override
    Page<TranAccount> findAll(Pageable pageable);


}
