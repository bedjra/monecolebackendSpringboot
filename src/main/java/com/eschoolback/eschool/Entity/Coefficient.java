package com.eschoolback.eschool.Entity;


import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Coefficient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NiveauEtude niveauEtude; // Seconde, Première, Terminale

    @Enumerated(EnumType.STRING)
    private Specialite specialite; // A4, D

    private String matiere;
    private double coefficient;


    @OneToMany(mappedBy = "coefficient", cascade = CascadeType.ALL)
    private List<Note> note;

    // Constructeurs
    public Coefficient() {}

    public Coefficient(NiveauEtude niveauEtude, Specialite specialite, String matiere, double coefficient) {
        this.niveauEtude = niveauEtude;
        this.specialite = specialite;
        this.matiere = matiere;
        this.coefficient = coefficient;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public NiveauEtude getNiveauEtude() { return niveauEtude; }
    public void setNiveauEtude(NiveauEtude niveauEtude) { this.niveauEtude = niveauEtude; }

    public Specialite getSpecialite() { return specialite; }
    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
}