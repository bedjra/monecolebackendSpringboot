package com.eschoolback.eschool.Entity;

import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;
    private Double resteEcolage;
    private Double montantDejaPaye;
    private Double montantActuel;


    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "scolarite_id")
    private Scolarite scolarite;


    @Enumerated(EnumType.STRING)
    private NiveauEtude niveau;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(Double resteEcolage) {
        this.resteEcolage = resteEcolage;
    }

    public Double getMontantDejaPaye() {
        return montantDejaPaye;
    }

    public void setMontantDejaPaye(Double montantDejaPaye) {
        this.montantDejaPaye = montantDejaPaye;
    }

    public Double getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(Double montantActuel) {
        this.montantActuel = montantActuel;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
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


}