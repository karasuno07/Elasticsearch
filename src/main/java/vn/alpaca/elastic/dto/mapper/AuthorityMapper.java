package vn.alpaca.elastic.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.elastic.dto.response.AuthorityResponse;
import vn.alpaca.elastic.entity.jpa.Authority;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorityMapper {

    AuthorityResponse authorityToAuthorityResponse(Authority authority);
}
