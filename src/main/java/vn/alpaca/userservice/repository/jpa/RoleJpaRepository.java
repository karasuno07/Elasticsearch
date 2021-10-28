package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.userservice.entity.jpa.Role;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends
        JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
