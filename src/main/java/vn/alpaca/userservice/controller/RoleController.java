package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.dto.request.RoleRequest;
import vn.alpaca.userservice.dto.response.RoleResponse;
import vn.alpaca.userservice.mapper.RoleMapper;
import vn.alpaca.userservice.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;
    private final RoleMapper mapper;

    @GetMapping
    ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> data = service.findAll().stream()
                .map(mapper::roleToRoleResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    ResponseEntity<RoleResponse> getRoleById(@PathVariable int roleId) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.findById(roleId));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("_search/name/{roleName}")
    ResponseEntity<RoleResponse> getRoleByName(@PathVariable String roleName) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.findByRoleName(roleName));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<RoleResponse> createRole(
            @RequestBody RoleRequest requestData
    ) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.create(requestData));

        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    ResponseEntity<RoleResponse> updateRole(
            @PathVariable int roleId,
            @RequestBody RoleRequest requestData
    ) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.update(roleId, requestData));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    ResponseEntity<Boolean> deleteRole(@PathVariable int roleId) {
        service.delete(roleId);
        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
