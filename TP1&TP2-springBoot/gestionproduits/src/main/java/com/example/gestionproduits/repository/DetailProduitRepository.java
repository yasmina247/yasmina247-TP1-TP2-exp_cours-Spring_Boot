package com.example.gestionproduits.repository;

import com.example.gestionproduits.entity.DetailProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailProduitRepository
        extends JpaRepository<DetailProduit, Long> {
}