package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.config.jwt.JwtProvider;
import vn.alpaca.userservice.dto.request.AuthenticationRequest;
import vn.alpaca.userservice.dto.response.UserResponse;
import vn.alpaca.userservice.dto.wrapper.AbstractResponse;
import vn.alpaca.userservice.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.dto.wrapper.TokenResponse;
import vn.alpaca.userservice.entity.cache.RefreshToken;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService tokenService;
    private final UserMapper mapper;

    @PostMapping("/login")
    AbstractResponse getToken(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = tokenService.createRefreshToken(user).getToken();
        UserResponse userInfo = mapper.userToUserResponse(user);

        TokenResponse response =
                new TokenResponse(accessToken, refreshToken, userInfo);

        return new SuccessResponse<>(response);
    }

    @PostMapping("/refresh-token/{token}")
    AbstractResponse refreshToken(@PathVariable String token) {
        RefreshToken refreshToken = tokenService.verifyExpiration(token);

        String accessToken = jwtProvider.generateToken(refreshToken.getUser());
        UserResponse userInfo = mapper.userToUserResponse(refreshToken.getUser());

        TokenResponse response = new TokenResponse(accessToken, refreshToken.getToken(), userInfo);

        return new SuccessResponse<>(response);
    }
}
