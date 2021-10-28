package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.dto.request.UserFilter;
import vn.alpaca.userservice.dto.request.UserRequest;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.dto.response.UserResponse;
import vn.alpaca.userservice.dto.wrapper.AbstractResponse;
import vn.alpaca.userservice.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    AbstractResponse
    getAllUser(@RequestBody Optional<UserFilter> filter) {
        Page<UserResponse> response =
                service.findAll(filter.orElse(new UserFilter()))
                        .map(mapper::userToUserResponse);

        return new SuccessResponse<>(response);
    }

    @GetMapping("/{userId}")
    AbstractResponse getUserById(@PathVariable int userId) {
        UserResponse response = mapper.userToUserResponse(
                service.findById(userId)
        );

        return new SuccessResponse<>(response);
    }

    @GetMapping("/_search/auth/{username}")
    AbstractResponse getUserByUsername(@PathVariable String username) {
        AuthenticationInfo response = mapper.userToAuthenInfo(
                service.findByUsername(username)
        );

        return new SuccessResponse<>(response);
    }

    @PostMapping
    AbstractResponse createUser(@RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.create(requestData)
        );

        return new SuccessResponse<>(HttpStatus.CREATED.value(), response);
    }

    @PutMapping("/{userId}")
    AbstractResponse updateUser(@PathVariable int userId,
                                @RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.update(userId, requestData)
        );

        return new SuccessResponse<>(response);
    }

    @PatchMapping("/{userId}/activate")
    AbstractResponse activateUser(@PathVariable int userId) {
        service.activate(userId);

        return new SuccessResponse<>(null);
    }

    @PatchMapping("/{userId}/deactivate")
    AbstractResponse deactivateUser(@PathVariable int userId) {
        service.deactivate(userId);

        return new SuccessResponse<>(null);
    }
}
