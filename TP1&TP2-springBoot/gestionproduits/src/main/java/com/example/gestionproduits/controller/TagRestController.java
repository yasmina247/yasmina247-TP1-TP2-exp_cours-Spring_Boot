package com.example.gestionproduits.controller;

import com.example.gestionproduits.entity.Tag;
import com.example.gestionproduits.repository.TagRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {

    private final TagRepository repository;

    public TagRestController(TagRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tag> getAll() {
        return repository.findAll();
    }
}