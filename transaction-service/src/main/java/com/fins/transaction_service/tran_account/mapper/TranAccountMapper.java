package com.fins.transaction_service.tran_account.mapper;

import com.fins.transaction_service.tran_account.dtos.TranAccountReq;
import com.fins.transaction_service.tran_account.dtos.TranAccountRes;
import com.fins.transaction_service.tran_account.model.TranAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TranAccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TranAccount toEntity(TranAccountReq req);

    TranAccountRes toResponse(TranAccount tranAccount);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(TranAccountReq req, @MappingTarget TranAccount tranAccount);
}
