package com.example.gestionproduits.controller;

import com.example.gestionproduits.entity.Fournisseur;
import com.example.gestionproduits.repository.FournisseurRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurRestController {

    private final FournisseurRepository repository;

    public FournisseurRestController(
            FournisseurRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Fournisseur> getAll() {
        return repository.findAll();
    }
}