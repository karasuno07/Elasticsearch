package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.userservice.dto.request.AuthenticationRequest;
import vn.alpaca.userservice.dto.wrapper.AbstractResponse;
import vn.alpaca.userservice.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.service.TokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    AbstractResponse getToken(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String accessToken = tokenService.createAccessToken(user);

        return new SuccessResponse<>(accessToken);
    }
}
