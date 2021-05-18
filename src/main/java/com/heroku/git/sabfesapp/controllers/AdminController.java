package com.heroku.git.sabfesapp.controllers;

import com.heroku.git.sabfesapp.models.role.ERole;
import com.heroku.git.sabfesapp.models.role.Role;
import com.heroku.git.sabfesapp.models.User;
import com.heroku.git.sabfesapp.payload.response.MessageResponse;
import com.heroku.git.sabfesapp.repository.RoleRepository;
import com.heroku.git.sabfesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
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

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delUser(@PathVariable String id) {
        Integer isRemoved = userRepository.deleteUserById(id);

        if (isRemoved == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find user!"));
        }

        return ResponseEntity
                .ok()
                .body(new MessageResponse(id));
    }

    @PutMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable String id, @RequestBody String newRole) {
        if (newRole == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role is null."));
        }

        if (userRepository.existsById(id)) {
            User user = userRepository.getOne(id);
            Set<Role> roles = new HashSet<>(user.getRoles());
            switch (newRole){
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;

                case "mod":
                    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                    break;

                default:
                    throw new RuntimeException("Error: Role is not found.");
            }
            user.setRoles(roles);
            userRepository.save(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find user."));
        }

        return ResponseEntity
                .ok()
                .body(new MessageResponse(id));
    }
}
