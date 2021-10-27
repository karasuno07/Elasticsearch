package vn.alpaca.userservice.dto.response;

import lombok.Data;

@Data
public class AuthenticationInfo {

    private int id;
    private String username;
    private String password;
    private boolean active;
}
