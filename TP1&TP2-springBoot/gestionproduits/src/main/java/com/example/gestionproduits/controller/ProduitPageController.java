package com.example.gestionproduits.controller;

import com.example.gestionproduits.repository.ProduitRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProduitPageController {

    private final ProduitRepository repository;

    public ProduitPageController(ProduitRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/produits")
    public String afficherProduits(Model model) {

        model.addAttribute(
                "produits",
                repository.findAll()
        );

        return "produits";
    }
} 