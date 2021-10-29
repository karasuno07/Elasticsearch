package vn.alpaca.userservice.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Payload;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.entity.jpa.Authority;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.entity.jpa.User_;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtProvider {

    @Value("${alpaca.security.jwt.secret:alpaca@@@alpaca@@@alpaca@@@alpaca@@@}")
    private String jwtSecret;

    @Value("${alpaca.security.jwt.expiration:300000}")
    private long jwtExpiration;  // 300000ms = 5 minutes as default

    public String generateToken(@NonNull User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(User_.ID, user.getId());
        payload.put(User_.USERNAME, user.getUsername());
        payload.put(User_.ROLE, user.getRole().getName());
        payload.put("authorities", user.getRole().getAuthorities().stream()
                .map(Authority::getPermissionName)
                .collect(Collectors.toList()));

        JWTCreator.Builder builder = JWT.create();
        builder.withKeyId(UUID.randomUUID().toString());
        builder.withPayload(payload);
        builder.withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration));

        return builder.sign(Algorithm.HMAC512(jwtSecret));
    }

    public AuthenticationInfo getAuthInfoFromToken(String token) {

        String json = new String(Base64.getDecoder()
                .decode(JWT.decode(token).getPayload()));
        Payload payload = new JWTParser().parsePayload(json);
        Map<String, Claim> claims = payload.getClaims();

        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setId(claims.get(User_.ID).asInt());
        authInfo.setUsername(claims.get(User_.USERNAME).asString());
        authInfo.setRoleName(claims.get(User_.ROLE).asString());
        authInfo.setAuthorities(new HashSet<>(claims.get("authorities")
                .asList(SimpleGrantedAuthority.class)));

        return authInfo;
    }

    public boolean validateToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC512(jwtSecret))
                    .build()
                    .verify(authToken);
            return true;
        } catch (JWTVerificationException | NullPointerException | IllegalArgumentException ex) {
            log.error("VALIDATE TOKEN FAILED: " + ex.getMessage());
            return false;
        }
    }
}
