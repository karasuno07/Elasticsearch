package vn.alpaca.elastic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.elastic.dto.request.UserRequest;
import vn.alpaca.elastic.dto.response.UserResponse;
import vn.alpaca.elastic.entity.es.UserES;
import vn.alpaca.elastic.entity.jpa.User;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    UserResponse userESToUserResponse(UserES userES);

    UserES userToUserES(User user);

    User userRequestToUser(UserRequest requestData);

    User userESToUser(UserES userES);
}
