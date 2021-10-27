package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.userservice.entity.jpa.Role;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends
        JpaRepository<Role, Integer> {

    @Override
    @Query("SELECT r FROM Role r " +
            "LEFT JOIN FETCH r.authorities")
    List<Role> findAll();

    @Override
    @Query("SELECT r FROM Role r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE r.id = ?1")
    Optional<Role> findById(Integer id);

    @Query("SELECT r FROM Role r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE r.name = ?1")
    Optional<Role> findByName(String name);
}
