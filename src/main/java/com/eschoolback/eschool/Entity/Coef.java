package com.eschoolback.eschool.Entity;


import com.eschoolback.eschool.enums.NiveauEtude;
import jakarta.persistence.*;


@Entity
public class Coef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matiere;

    @Enumerated(EnumType.STRING)
    private NiveauEtude niveau;

    private int coefficient;

    public Coef() {}

    public Coef(String matiere, NiveauEtude niveau, int coefficient) {
        this.matiere = matiere;
        this.niveau = niveau;
        this.coefficient = coefficient;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public NiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
}
