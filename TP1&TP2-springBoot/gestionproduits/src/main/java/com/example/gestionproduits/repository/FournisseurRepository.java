package com.example.gestionproduits.repository;

import com.example.gestionproduits.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository
        extends JpaRepository<Fournisseur, Long> {
}