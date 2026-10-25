package com.example.gestionproduits.repository;

import com.example.gestionproduits.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository
        extends JpaRepository<Categorie, Long> {
}