
package com.example.gestionproduits.service;

import com.example.gestionproduits.model.Role;
import com.example.gestionproduits.model.User;
import com.example.gestionproduits.repository.RoleRepository;
import com.example.gestionproduits.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class InitData {

    @Bean
    ApplicationRunner initUsers(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            createRoleIfMissing(roleRepository, "ADMIN");
            createRoleIfMissing(roleRepository, "PHARMACIEN");
            createRoleIfMissing(roleRepository, "CAISSIER");
            createRoleIfMissing(roleRepository, "CLIENT");

            createUserIfMissing(
                userRepository, roleRepository, passwordEncoder,
                "admin", "admin123", "ADMIN");

            createUserIfMissing(
                userRepository, roleRepository, passwordEncoder,
                "pharma", "pharma123", "PHARMACIEN");

            createUserIfMissing(
                userRepository, roleRepository, passwordEncoder,
                "caisse", "caisse123", "CAISSIER");

            createUserIfMissing(
                userRepository, roleRepository, passwordEncoder,
                "client", "client123", "CLIENT");
        };
    }

    private void createRoleIfMissing(
            RoleRepository roleRepository, String name) {

        if (roleRepository.findByName(name).isEmpty()) {
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    private void createUserIfMissing(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String rawPassword,
            String roleName) {

        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
            .orElseThrow();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(new HashSet<>(Set.of(role)));

        userRepository.save(user);
    }
}
