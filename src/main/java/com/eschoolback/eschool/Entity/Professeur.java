package com.eschoolback.eschool.Entity;

import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import jakarta.persistence.*;

@Entity
public class Professeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String matiere;

    @Enumerated(EnumType.STRING)
    private NiveauEtude niveau;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;


    // Constructeurs
    public Professeur() {}

    public Professeur(String nom, String matiere) {
        this.nom = nom;
        this.matiere = matiere;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }
}
