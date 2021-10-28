package vn.alpaca.userservice.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.alpaca.userservice.dto.response.UserResponse;

@Data
@AllArgsConstructor
public final class TokenResponse {

    @JsonProperty("access_token")
    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String type = "Bearer";

    @JsonProperty("user_info")
    private UserResponse userInfo;

    public TokenResponse(String token,
                         String refreshToken,
                         UserResponse userInfo) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userInfo = userInfo;
    }
}
