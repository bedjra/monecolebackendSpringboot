package com.eschoolback.eschool.Entity;

import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import jakarta.persistence.*;

@Entity
public class Scolarite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NiveauEtude niveau;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    private Double montant;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
