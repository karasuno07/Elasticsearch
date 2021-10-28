package vn.alpaca.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class AuthenticationInfo {

    private int id;

    private String username;

    private String password;

    private boolean active;

    @JsonProperty("role")
    private String roleName;

    private Set<String> permissions;
}
