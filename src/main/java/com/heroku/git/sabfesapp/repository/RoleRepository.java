package com.heroku.git.sabfesapp.repository;

import com.heroku.git.sabfesapp.models.role.ERole;
import com.heroku.git.sabfesapp.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Role getRoleByName(ERole name);
}
