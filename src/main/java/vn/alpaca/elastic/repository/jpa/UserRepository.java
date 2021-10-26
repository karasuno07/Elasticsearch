package vn.alpaca.elastic.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.elastic.entity.jpa.User;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
