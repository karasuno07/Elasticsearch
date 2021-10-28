package vn.alpaca.userservice.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.JWTParser;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.entity.jpa.Authority;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.entity.jpa.User_;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtProvider {

    @Value("${alpaca.security.jwt.secret:alpaca@@@alpaca@@@alpaca@@@alpaca@@@}")
    private String jwtSecret;

    @Value("${alpaca.security.jwt.expiration:5000}")
    private long jwtExpiration;

    public String generateToken(@NonNull User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(User_.USERNAME, user.getUsername());
        payload.put(User_.PASSWORD, user.getPassword());
        payload.put(User_.ACTIVE, user.isActive());
        payload.put(User_.ROLE, user.getRole().getName());
        payload.put("permissions", user.getRole().getAuthorities().stream()
                .map(Authority::getPermissionName)
                .collect(Collectors.toList())
        );

        JWTCreator.Builder builder = JWT.create();
        builder.withKeyId(UUID.randomUUID().toString());
        builder.withJWTId(Integer.toString(user.getId()));
        builder.withPayload(payload);
        builder.withExpiresAt(
                new Date(System.currentTimeMillis() + jwtExpiration)
        );

        return builder.sign(Algorithm.HMAC512(jwtSecret));
    }

    public String getUserIdFromToken(String token) {
        return JWT.decode(token).getId();
    }

    public AuthenticationInfo getAuthInfoFromToken(String token) {
        String payload = JWT.decode(token).getPayload();
        return (AuthenticationInfo) new JWTParser().parsePayload(payload);
    }

    public boolean validateToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC512(jwtSecret))
                    .build()
                    .verify(authToken);
            return true;
        } catch (JWTVerificationException | NullPointerException | IllegalArgumentException ex) {
            return false;
        }
    }
}
