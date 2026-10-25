
package com.example.gestionproduits.controller;

import com.example.gestionproduits.model.Role;
import com.example.gestionproduits.model.User;
import com.example.gestionproduits.repository.RoleRepository;
import com.example.gestionproduits.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin";
    }

    @GetMapping("/users")
    public String afficherUtilisateurs(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-users";
    }

    @PostMapping("/users")
    public String ajouterUtilisateur(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String roleName,
            RedirectAttributes redirectAttributes) {

        username = username.trim();

        if (username.isBlank() || password.isBlank()) {
            redirectAttributes.addFlashAttribute(
                "erreur", "L'identifiant et le mot de passe sont obligatoires.");
            return "redirect:/admin/users";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute(
                "erreur", "Cet identifiant existe déjà.");
            return "redirect:/admin/users";
        }

        Role role = roleRepository.findByName(roleName)
            .orElse(null);

        if (role == null) {
            redirectAttributes.addFlashAttribute(
                "erreur", "Rôle inconnu.");
            return "redirect:/admin/users";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(new HashSet<>(Set.of(role)));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
            "message", "Utilisateur ajouté avec succès.");

        return "redirect:/admin/users";
    }
}
