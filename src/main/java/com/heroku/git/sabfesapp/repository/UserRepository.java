package com.heroku.git.sabfesapp.repository;

import com.heroku.git.sabfesapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    User getByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Integer deleteUserById(String id);
}
