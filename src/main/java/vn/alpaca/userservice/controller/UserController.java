package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.dto.request.UserFilter;
import vn.alpaca.userservice.dto.request.UserRequest;
import vn.alpaca.userservice.dto.response.AuthenticationInfo;
import vn.alpaca.userservice.dto.response.UserResponse;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>>
    getAllUser(@RequestBody Optional<UserFilter> filter) {
        Page<UserResponse> response =
                service.findAll(filter.orElse(new UserFilter()))
                        .map(mapper::userToUserResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse>
    getUserById(@PathVariable int userId) {
        UserResponse response = mapper.userToUserResponse(
                service.findById(userId)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/_search/auth/{username}")
    public ResponseEntity<AuthenticationInfo>
    getUserByUsername(@PathVariable String username) {
        AuthenticationInfo response = mapper.userToAuthenInfo(
                service.findByUsername(username)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse>
    createUser(@RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.create(requestData)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse>
    updateUser(@PathVariable int userId, @RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.update(userId, requestData)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Boolean>
    activateUser(@PathVariable int userId) {
        service.activate(userId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Boolean>
    deactivateUser(@PathVariable int userId) {
        service.deactivate(userId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
