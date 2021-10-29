package vn.alpaca.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

@Data
public class AuthenticationInfo implements Serializable {

    private int id;

    private String username;

    @JsonProperty("role")
    private String roleName;

    private Set<? extends GrantedAuthority> authorities;
}
