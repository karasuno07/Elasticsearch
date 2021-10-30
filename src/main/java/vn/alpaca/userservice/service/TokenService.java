package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.entity.jpa.User;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {

    private final RedissonClient client;

    public final String TOKEN_CACHE_NAME = "user_token";
    private RMap<String, User> accessTokenMap;

    @PostConstruct

    public void init() {
        accessTokenMap = client.getMap(TOKEN_CACHE_NAME);
    }

    public String createAccessToken(User user) {
        String token = UUID.randomUUID().toString();
        accessTokenMap.fastPut(token, user);
        return token;
    }

    public User retrieveUserFromToken(String token) {
        if (accessTokenMap.containsKey(token)) {
            return accessTokenMap.get(token);
        } else {
            throw new AccessDeniedException("Token has been invalidated");
        }
    }

    public void removeToken(String token) {
        accessTokenMap.remove(token);
    }

    public void removeToken(User user) {
        String token = accessTokenMap
                .readAllEntrySet().stream().distinct()
                .filter(entry -> entry.getValue().getId() == user.getId())
                .findFirst().map(Map.Entry::getKey).toString();
        accessTokenMap.remove(token);
    }

}
