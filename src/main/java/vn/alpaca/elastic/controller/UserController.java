package vn.alpaca.elastic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.elastic.dto.response.UserResponse;
import vn.alpaca.elastic.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return new ResponseEntity<>(
                service.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse>
    getUserById(@PathVariable int userId) {
        return new ResponseEntity<>(
                service.findById(userId),
                HttpStatus.OK
        );
    }
}
