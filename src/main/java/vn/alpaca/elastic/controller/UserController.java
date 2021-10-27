package vn.alpaca.elastic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.elastic.dto.request.UserFilter;
import vn.alpaca.elastic.dto.request.UserRequest;
import vn.alpaca.elastic.dto.response.UserResponse;
import vn.alpaca.elastic.mapper.UserMapper;
import vn.alpaca.elastic.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUser() {
        Page<UserResponse> response =
                service.findAll(new UserFilter(), Pageable.unpaged())
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

    @GetMapping("/_search")
    public ResponseEntity<UserResponse>
    getUserByFields(@RequestParam String username) {
        UserResponse response = mapper.userToUserResponse(
                service.findByUsername(username)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse>
    createUser(@RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.createUser(requestData)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse>
    updateUser(@PathVariable int userId, @RequestBody UserRequest requestData) {
        UserResponse response = mapper.userToUserResponse(
                service.updateUser(userId, requestData)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Boolean>
    activateUser(@PathVariable int userId) {
        service.activateUser(userId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Boolean>
    deactivateUser(@PathVariable int userId) {
        service.deactivateUser(userId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
