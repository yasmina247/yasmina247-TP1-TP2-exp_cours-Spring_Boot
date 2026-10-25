
package com.example.gestionproduits.controller;

import com.example.gestionproduits.repository.ProduitRepository;
import com.example.gestionproduits.repository.VenteRepository;
import com.example.gestionproduits.service.VenteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VenteController {

    private final ProduitRepository produitRepository;
    private final VenteRepository venteRepository;
    private final VenteService venteService;

    public VenteController(
            ProduitRepository produitRepository,
            VenteRepository venteRepository,
            VenteService venteService) {
        this.produitRepository = produitRepository;
        this.venteRepository = venteRepository;
        this.venteService = venteService;
    }

    @GetMapping("/ventes")
    public String afficherVentes(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        model.addAttribute(
            "ventes", venteRepository.findAllByOrderByDateVenteDesc());
        return "ventes";
    }

    @PostMapping("/ventes")
    public String enregistrerVente(
            @RequestParam Long produitId,
            @RequestParam Integer quantite,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            venteService.enregistrerVente(
                produitId, quantite, authentication.getName());

            redirectAttributes.addFlashAttribute(
                "message", "Vente enregistrée et stock mis à jour.");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                "erreur", e.getMessage());
        }

        return "redirect:/ventes";
    }
}
