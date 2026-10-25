package com.example.gestionproduits.repository;

import com.example.gestionproduits.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository
        extends JpaRepository<Tag, Long> {
}