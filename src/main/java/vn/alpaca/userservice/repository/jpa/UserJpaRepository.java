package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.userservice.entity.jpa.User;

import java.util.Optional;

public interface UserJpaRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    @Override
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.role AS r " +
            "LEFT JOIN FETCH r.authorities")
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Override
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.role AS r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE u.id = ?1")
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.role AS r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
}
