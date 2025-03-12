package com.eschoolback.eschool.Entity;

import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eleveMatricule;

    @Column(nullable = false)
    private String eleveNom;

    @Column(nullable = false)
    private String elevePrenom;

    @Column(nullable = false)
    private String eleveAdresse;

    private LocalDate eleveDateNaiss;

    @Column(nullable = false)
    private String eleveLieuNais;

    @Column(nullable = false)
    private String eleveSexe;

    @Column(nullable = false)
    private String eleveEtatProvenance;

    private LocalDate eleveDateIns;

    @Column(nullable = false)
    private String tuteurNom;

    @Column(nullable = false)
    private String tuteurPrenom;

    @Column(nullable = false)
    private String tuteurProfession;

    @Column(nullable = false)
    private String tuteurAdresse;

    private String tuteurTelDom;

    @Column(nullable = false)
    private String tuteurCel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NiveauEtude niveauEtude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialite specialite;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEleveMatricule() {
        return eleveMatricule;
    }

    public void setEleveMatricule(String eleveMatricule) {
        this.eleveMatricule = eleveMatricule;
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

    public String getEleveAdresse() {
        return eleveAdresse;
    }

    public void setEleveAdresse(String eleveAdresse) {
        this.eleveAdresse = eleveAdresse;
    }

    public LocalDate getEleveDateNaiss() {
        return eleveDateNaiss;
    }

    public void setEleveDateNaiss(LocalDate eleveDateNaiss) {
        this.eleveDateNaiss = eleveDateNaiss;
    }

    public String getEleveLieuNais() {
        return eleveLieuNais;
    }

    public void setEleveLieuNais(String eleveLieuNais) {
        this.eleveLieuNais = eleveLieuNais;
    }

    public String getEleveSexe() {
        return eleveSexe;
    }

    public void setEleveSexe(String eleveSexe) {
        this.eleveSexe = eleveSexe;
    }

    public String getEleveEtatProvenance() {
        return eleveEtatProvenance;
    }

    public void setEleveEtatProvenance(String eleveEtatProvenance) {
        this.eleveEtatProvenance = eleveEtatProvenance;
    }

    public LocalDate getEleveDateIns() {
        return eleveDateIns;
    }

    public void setEleveDateIns(LocalDate eleveDateIns) {
        this.eleveDateIns = eleveDateIns;
    }

    public String getTuteurNom() {
        return tuteurNom;
    }

    public void setTuteurNom(String tuteurNom) {
        this.tuteurNom = tuteurNom;
    }

    public String getTuteurPrenom() {
        return tuteurPrenom;
    }

    public void setTuteurPrenom(String tuteurPrenom) {
        this.tuteurPrenom = tuteurPrenom;
    }

    public String getTuteurProfession() {
        return tuteurProfession;
    }

    public void setTuteurProfession(String tuteurProfession) {
        this.tuteurProfession = tuteurProfession;
    }

    public String getTuteurAdresse() {
        return tuteurAdresse;
    }

    public void setTuteurAdresse(String tuteurAdresse) {
        this.tuteurAdresse = tuteurAdresse;
    }

    public String getTuteurTelDom() {
        return tuteurTelDom;
    }

    public void setTuteurTelDom(String tuteurTelDom) {
        this.tuteurTelDom = tuteurTelDom;
    }

    public String getTuteurCel() {
        return tuteurCel;
    }

    public void setTuteurCel(String tuteurCel) {
        this.tuteurCel = tuteurCel;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
}
