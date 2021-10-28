package vn.alpaca.userservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.userservice.dto.request.UserRequest;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.dto.response.UserResponse;
import vn.alpaca.userservice.entity.es.UserES;
import vn.alpaca.userservice.entity.jpa.Authority;
import vn.alpaca.userservice.entity.jpa.User;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    UserResponse userESToUserResponse(UserES userES);

    AuthenticationInfo userToAuthenInfo(User user);

    UserES userToUserES(User user);

    User userRequestToUser(UserRequest requestData);

    User userESToUser(UserES userES);

    @AfterMapping
    default void getResponseRoleNameFromUserEntity(
            @MappingTarget UserResponse response,
            User user
    ) {
        response.setRoleName(user.getRole().getName());
    }

    @AfterMapping
    default void getAuthRoleNameFromUserEntity(
            @MappingTarget AuthenticationInfo authInfo,
            User user
    ) {
        authInfo.setRoleName(user.getRole().getName());

        Set<String> permissions = user.getRole().getAuthorities().stream()
                .map(Authority::getPermissionName)
                .collect(Collectors.toSet());
        authInfo.setPermissions(permissions);
    }
}
