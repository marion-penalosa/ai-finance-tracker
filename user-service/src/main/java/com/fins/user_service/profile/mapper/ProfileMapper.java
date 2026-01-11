package com.fins.user_service.profile.mapper;

import com.fins.user_service.profile.dtos.ProfileReq;
import com.fins.user_service.profile.dtos.ProfileRes;
import com.fins.user_service.profile.dtos.UpdateProfileReq;
import com.fins.user_service.profile.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "account", ignore = true)
    Profile toEntity(ProfileReq req);

    ProfileRes toResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "account", ignore = true)
    void updateEntityFromRequest(UpdateProfileReq req, @MappingTarget Profile profile);
}
