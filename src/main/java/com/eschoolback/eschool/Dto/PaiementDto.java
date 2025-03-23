package com.eschoolback.eschool.Dto;

import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;

import java.time.LocalDate;

public class PaiementDto {

    private Long eleveId;
    private String eleveNom;
    private String elevePrenom;
    private String eleveMatricule;
    private Specialite specialite;
    private NiveauEtude niveauEtude;
    private Scolarite scolarite;
    private LocalDate datePaiement;
    private long montantActuel;
    private long resteEcolage;
    private long montantDejaPaye;


    public PaiementDto(Long eleveId, String eleveNom, String elevePrenom, String eleveMatricule,
                       Specialite specialite, NiveauEtude niveauEtude, Scolarite scolarite,
                       LocalDate datePaiement, long montantActuel, long resteEcolage, long montantDejaPaye) {
        this.eleveId = eleveId;
        this.eleveNom = eleveNom;
        this.elevePrenom = elevePrenom;
        this.eleveMatricule = eleveMatricule;
        this.specialite = specialite;
        this.niveauEtude = niveauEtude;
        this.scolarite = scolarite;
        this.datePaiement = datePaiement;
        this.montantActuel = montantActuel;
        this.resteEcolage = resteEcolage;
        this.montantDejaPaye = montantDejaPaye; // ✅ Ajout de ce champ
    }


    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public String getEleveNom() {
        return eleveNom;
    }

    public void setEleveNom(String eleveNom) {
        this.eleveNom = eleveNom;
    }

    public String getElevePrenom() {
        return elevePrenom;
    }

    public void setElevePrenom(String elevePrenom) {
        this.elevePrenom = elevePrenom;
    }

    public String getEleveMatricule() {
        return eleveMatricule;
    }

    public void setEleveMatricule(String eleveMatricule) {
        this.eleveMatricule = eleveMatricule;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public long getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(long montantActuel) {
        this.montantActuel = montantActuel;
    }

    public long getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(long resteEcolage) {
        this.resteEcolage = resteEcolage;
    }



    public long getMontantDejaPaye() {
        return montantDejaPaye;
    }

    public void setMontantDejaPaye(long montantDejaPaye) {
        this.montantDejaPaye = montantDejaPaye;
    }
}
