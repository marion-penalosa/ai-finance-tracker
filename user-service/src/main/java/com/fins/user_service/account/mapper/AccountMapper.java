package com.fins.user_service.account.mapper;


import com.fins.user_service.account.dtos.AccountReq;
import com.fins.user_service.account.dtos.AccountRes;
import com.fins.user_service.account.dtos.UpdateAccountReq;
import com.fins.user_service.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Account toEntity(AccountReq req);

    AccountRes toResponse(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateAccountReq req, @MappingTarget Account account);

}
