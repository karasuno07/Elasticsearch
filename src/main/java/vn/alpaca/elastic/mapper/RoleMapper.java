package vn.alpaca.elastic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.elastic.dto.response.RoleResponse;
import vn.alpaca.elastic.entity.jpa.Role;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleResponse roleToRoleResponse(Role role);
}
