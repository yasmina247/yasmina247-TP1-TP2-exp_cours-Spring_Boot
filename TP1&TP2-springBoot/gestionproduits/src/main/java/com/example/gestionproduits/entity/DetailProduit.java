package com.example.gestionproduits.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fabricant;

    private String paysOrigine;

    private String composition;

    private String dosage;

    private String indications;

    @JsonIgnore
    @OneToOne(mappedBy = "detailProduit")
    private Produit produit;
}