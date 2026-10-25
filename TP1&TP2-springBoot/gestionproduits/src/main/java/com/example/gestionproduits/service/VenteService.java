
package com.example.gestionproduits.service;

import com.example.gestionproduits.entity.Produit;
import com.example.gestionproduits.model.Vente;
import com.example.gestionproduits.repository.ProduitRepository;
import com.example.gestionproduits.repository.VenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class VenteService {

    private final ProduitRepository produitRepository;
    private final VenteRepository venteRepository;

    public VenteService(
            ProduitRepository produitRepository,
            VenteRepository venteRepository) {
        this.produitRepository = produitRepository;
        this.venteRepository = venteRepository;
    }

    @Transactional
    public void enregistrerVente(
            Long produitId, Integer quantite, String vendeur) {

        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException(
                "La quantité doit être supérieure à zéro.");
        }

        Produit produit = produitRepository.findById(produitId)
            .orElseThrow(() ->
                new IllegalArgumentException("Produit introuvable."));

        int stock = produit.getQuantiteStock() == null
            ? 0 : produit.getQuantiteStock();

        if (stock < quantite) {
            throw new IllegalArgumentException(
                "Stock insuffisant. Stock disponible : " + stock);
        }

        BigDecimal prix = BigDecimal.valueOf(produit.getPrix());

        if (prix == null || prix.signum() < 0) {
            throw new IllegalArgumentException(
                "Le prix du produit est invalide.");
        }

        BigDecimal total = prix.multiply(
            BigDecimal.valueOf(quantite));

        produit.setQuantiteStock(stock - quantite);
        produitRepository.save(produit);

        Vente vente = new Vente();
        vente.setProduit(produit);
        vente.setQuantite(quantite);
        vente.setMontantTotal(total);
        vente.setVendeur(vendeur);
        vente.setDateVente(LocalDateTime.now());

        venteRepository.save(vente);
    }
}
