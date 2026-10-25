package com.example.gestionproduits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionproduits.entity.Produit;

public interface ProduitRepository
        extends JpaRepository<Produit, Long> {
}