package com.example.gestionproduits.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String contact;

    private String adresse;

    @OneToMany(mappedBy = "fournisseur")
    @JsonIgnore
    private List<Produit> produits;
}