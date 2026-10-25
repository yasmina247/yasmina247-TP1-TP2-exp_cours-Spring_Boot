package com.example.gestionproduits.controller;

import com.example.gestionproduits.entity.Categorie;
import com.example.gestionproduits.repository.CategorieRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieRestController {

    private final CategorieRepository repository;

    public CategorieRestController(
            CategorieRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Categorie> getAll() {
        return repository.findAll();
    }
    @PostMapping
    public ResponseEntity<Categorie> create(
        @RequestBody Categorie categorie) {

    Categorie saved = repository.save(categorie);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(saved);
}
}