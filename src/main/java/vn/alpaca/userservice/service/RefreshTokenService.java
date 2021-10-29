package vn.alpaca.userservice.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.entity.cache.RefreshToken;
import vn.alpaca.userservice.entity.jpa.User;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedissonClient client;

    public final String CACHE_NAME = "user_refresh_token";
    private final TimeUnit unit = TimeUnit.HOURS;

    @Value("${alpaca.security.jwt.refresh-token-ttl:12}")
    private long timeToLive;

    private RMapCache<String, RefreshToken> tokenMap;

    @PostConstruct
    public void init() {
        tokenMap = client.getMapCache(CACHE_NAME);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);

        tokenMap.fastPut(token.getToken(), token, timeToLive, unit);
        log.info("SAVED TOKEN: " + tokenMap.get(token.getToken()));

        return token;
    }

    public RefreshToken verifyExpiration(String token) {
        if (tokenMap.remainTimeToLive(token) > 0) {
            RefreshToken currentToken = tokenMap.get(token);
            User user = currentToken.getUser();

            tokenMap.remove(token);

            return createRefreshToken(user);
        } else {
            throw new TokenExpiredException("Refresh token has been expired");
        }
    }

}
