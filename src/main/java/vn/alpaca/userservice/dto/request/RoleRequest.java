package vn.alpaca.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class RoleRequest {

    @JsonProperty("role_name")
    private String name;

    @JsonProperty("authority_ids")
    private Set<Integer> authorityIds;
}
