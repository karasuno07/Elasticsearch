package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.dto.request.RoleRequest;
import vn.alpaca.userservice.dto.response.RoleResponse;
import vn.alpaca.userservice.dto.wrapper.AbstractResponse;
import vn.alpaca.userservice.dto.wrapper.SuccessResponse;
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
    AbstractResponse getAllRoles() {
        List<RoleResponse> data = service.findAll().stream()
                .map(mapper::roleToRoleResponse)
                .collect(Collectors.toList());

        return new SuccessResponse<>(data);
    }

    @GetMapping("/{roleId}")
    AbstractResponse getRoleById(@PathVariable int roleId) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.findById(roleId));

        return new SuccessResponse<>(data);
    }

    @GetMapping("_search/name/{roleName}")
    AbstractResponse getRoleByName(@PathVariable String roleName) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.findByRoleName(roleName));

        return new SuccessResponse<>(data);
    }

    @PostMapping
    AbstractResponse createRole(
            @RequestBody RoleRequest requestData
    ) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.create(requestData));

        return new SuccessResponse<>(HttpStatus.CREATED.value(), data);
    }

    @PutMapping("/{roleId}")
    AbstractResponse updateRole(
            @PathVariable int roleId,
            @RequestBody RoleRequest requestData
    ) {
        RoleResponse data = mapper
                .roleToRoleResponse(service.update(roleId, requestData));

        return new SuccessResponse<>(data);
    }

    @DeleteMapping("/{roleId}")
    AbstractResponse deleteRole(@PathVariable int roleId) {
        service.delete(roleId);
        return new SuccessResponse<>(null);
    }
}
