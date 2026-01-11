package com.fins.transaction_service.transaction.mapper;

import com.fins.transaction_service.transaction.dtos.TransactionReq;
import com.fins.transaction_service.transaction.dtos.TransactionRes;
import com.fins.transaction_service.transaction.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    Transaction toEntity(TransactionReq req);

    TransactionRes toResponse(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(TransactionReq req, @MappingTarget Transaction transaction);

}
