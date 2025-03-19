package com.eschoolback.eschool.Dto;

import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.enums.StatutScolarite;

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
    private StatutScolarite StatutScolarite ;
    private long montantActuel;
    private long resteEcolage;
    private long montantAChanger;


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

    public com.eschoolback.eschool.enums.StatutScolarite getStatutScolarite() {
        return StatutScolarite;
    }

    public void setStatutScolarite(com.eschoolback.eschool.enums.StatutScolarite statutScolarite) {
        StatutScolarite = statutScolarite;
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

    public long getMontantAChanger() {
        return montantAChanger;
    }

    public void setMontantAChanger(long montantAChanger) {
        this.montantAChanger = montantAChanger;
    }
}
