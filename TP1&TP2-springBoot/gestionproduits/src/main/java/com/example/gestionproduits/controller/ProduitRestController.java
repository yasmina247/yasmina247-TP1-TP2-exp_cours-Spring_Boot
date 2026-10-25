package com.example.gestionproduits.controller;

import com.example.gestionproduits.entity.Produit;
import com.example.gestionproduits.repository.ProduitRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {

    private final ProduitRepository produitRepository;

    public ProduitRestController(
            ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // 1. Lister tous les produits
    @GetMapping
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    // 2. Afficher un produit par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(
            @PathVariable Long id) {

        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Ajouter un produit
    @PostMapping
    public ResponseEntity<Produit> create(
            @RequestBody Produit produit) {

        Produit saved = produitRepository.save(produit);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // 4. Modifier un produit
    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(
            @PathVariable Long id,
            @RequestBody Produit nouveauProduit) {

        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setNom(nouveauProduit.getNom());
                    produit.setDescription(
                            nouveauProduit.getDescription());
                    produit.setPrix(nouveauProduit.getPrix());
                    produit.setCodeBarre(
                            nouveauProduit.getCodeBarre());
                    produit.setDateExpiration(
                            nouveauProduit.getDateExpiration());
                    produit.setCategorie(
                            nouveauProduit.getCategorie());
                    produit.setFournisseur(
                            nouveauProduit.getFournisseur());
                    produit.setDetailProduit(
                            nouveauProduit.getDetailProduit());
                    produit.setTags(nouveauProduit.getTags());

                    Produit saved =
                            produitRepository.save(produit);

                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        if (!produitRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produitRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}