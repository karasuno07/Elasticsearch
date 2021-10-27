package vn.alpaca.userservice.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.userservice.dto.request.UserRequest;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.dto.response.UserResponse;
import vn.alpaca.userservice.entity.es.UserES;
import vn.alpaca.userservice.entity.jpa.User;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    UserResponse userESToUserResponse(UserES userES);

    AuthenticationInfo userToAuthenInfo(User user);

    UserES userToUserES(User user);

    User userRequestToUser(UserRequest requestData);

    User userESToUser(UserES userES);

    @BeforeMapping
    default void getRoleNameFromUserEntity(
            @MappingTarget UserResponse response,
            User user
    ) {
        response.setRoleName(user.getRole().getName());
    }
}
