package com.heroku.git.sabfesapp;

import com.heroku.git.sabfesapp.controllers.AuthController;
import com.heroku.git.sabfesapp.models.User;
import com.heroku.git.sabfesapp.models.role.ERole;
import com.heroku.git.sabfesapp.models.role.Role;
import com.heroku.git.sabfesapp.payload.request.SignupRequest;
import com.heroku.git.sabfesapp.repository.RoleRepository;
import com.heroku.git.sabfesapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthTests {
    TestEntityManager entityManager;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEntityManager(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertThat(roleRepository).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();
    }

    @Test
    public void signupTest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("UserTest");
        signupRequest.setEmail("usertest@mail.ru");
        signupRequest.setPassword("usertest");
        Set<String> rolesStr = new HashSet<>();
        rolesStr.add("user");
        signupRequest.setRole(rolesStr);

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword());

        Set<Role> roles = new HashSet<>();
        rolesStr.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.getRoleByName(ERole.ROLE_ADMIN);
                    roles.add(adminRole);
                    break;

                case "mod":
                    Role modRole = roleRepository.getRoleByName(ERole.ROLE_MODERATOR);
                    roles.add(modRole);
                    break;

                default:
                    Role userRole = roleRepository.getRoleByName(ERole.ROLE_USER);
                    roles.add(userRole);
            }
        });

        int wasSize = userRepository.findAll().size();

        user.setRoles(roles);
        userRepository.save(user);

        Assertions.assertThat(userRepository.findAll()).hasSize(wasSize + 1);
        Assertions.assertThat(userRepository.findAll()).contains(user);
    }

    @Test
    public void wrongEmailSignupTest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("UserTest2");
        signupRequest.setEmail("usertestmail.ru");
        signupRequest.setPassword("usertest2");
        Set<String> rolesStr = new HashSet<>();
        rolesStr.add("user");
        signupRequest.setRole(rolesStr);

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword());

        Set<Role> roles = new HashSet<>();
        rolesStr.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.getRoleByName(ERole.ROLE_ADMIN);
                    roles.add(adminRole);
                    break;

                case "mod":
                    Role modRole = roleRepository.getRoleByName(ERole.ROLE_MODERATOR);
                    roles.add(modRole);
                    break;

                default:
                    Role userRole = roleRepository.getRoleByName(ERole.ROLE_USER);
                    roles.add(userRole);
            }
        });

        int wasSize = userRepository.findAll().size();

        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (ConstraintViolationException ignored) {
            Assertions.assertThat(userRepository.findAll()).hasSize(wasSize);
            Assertions.assertThat(userRepository.findAll()).doesNotContain(user);
        }
    }

    @Test
    public void blankPasswordSignupTest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("UserTest3");
        signupRequest.setEmail("usertest@mail.ru");
        signupRequest.setPassword("");
        Set<String> rolesStr = new HashSet<>();
        rolesStr.add("user");
        signupRequest.setRole(rolesStr);

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword());

        Set<Role> roles = new HashSet<>();
        rolesStr.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.getRoleByName(ERole.ROLE_ADMIN);
                    roles.add(adminRole);
                    break;

                case "mod":
                    Role modRole = roleRepository.getRoleByName(ERole.ROLE_MODERATOR);
                    roles.add(modRole);
                    break;

                default:
                    Role userRole = roleRepository.getRoleByName(ERole.ROLE_USER);
                    roles.add(userRole);
            }
        });

        int wasSize = userRepository.findAll().size();

        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (ConstraintViolationException ignored) {
            Assertions.assertThat(userRepository.findAll()).hasSize(wasSize);
            Assertions.assertThat(userRepository.findAll()).doesNotContain(user);
        }
    }
}
