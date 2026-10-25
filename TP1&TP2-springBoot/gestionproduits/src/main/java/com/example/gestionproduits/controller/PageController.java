
package com.example.gestionproduits.controller;

import com.example.gestionproduits.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProduitRepository produitRepository;

    public PageController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/produits/tous")
    public String produits(Model model) {
        model.addAttribute("produits",
            produitRepository.findAll());
        return "produits";
    }
}
