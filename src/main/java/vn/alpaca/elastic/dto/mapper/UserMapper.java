package vn.alpaca.elastic.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.elastic.dto.response.UserResponse;
import vn.alpaca.elastic.entity.es.EsUser;
import vn.alpaca.elastic.entity.jpa.User;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    UserResponse esUserToUserResponse(EsUser esUser);

    EsUser userToEsUser(User user);
}
