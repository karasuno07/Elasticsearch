package vn.alpaca.elastic.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.elastic.entity.jpa.Role;

public interface RoleRepository extends
        JpaRepository<Role, Integer> {
}
