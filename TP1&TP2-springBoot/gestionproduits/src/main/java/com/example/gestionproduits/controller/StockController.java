
package com.example.gestionproduits.controller;

import com.example.gestionproduits.entity.Produit;
import com.example.gestionproduits.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StockController {

    private final ProduitRepository produitRepository;

    public StockController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping("/stocks")
    public String afficherStocks(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "stocks";
    }

    @PostMapping("/stocks/{id}")
    public String modifierStock(
            @PathVariable Long id,
            @RequestParam Integer quantiteStock,
            RedirectAttributes redirectAttributes) {

        if (quantiteStock < 0) {
            redirectAttributes.addFlashAttribute(
                "erreur", "La quantité ne peut pas être négative.");
            return "redirect:/stocks";
        }

        Produit produit = produitRepository.findById(id).orElse(null);

        if (produit == null) {
            redirectAttributes.addFlashAttribute(
                "erreur", "Produit introuvable.");
            return "redirect:/stocks";
        }

        produit.setQuantiteStock(quantiteStock);
        produitRepository.save(produit);

        redirectAttributes.addFlashAttribute(
            "message", "Stock mis à jour avec succès.");

        return "redirect:/stocks";
    }
}
