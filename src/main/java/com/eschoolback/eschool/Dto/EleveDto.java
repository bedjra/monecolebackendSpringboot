package com.eschoolback.eschool.Dto;

import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import java.time.LocalDate;

public class EleveDto {
    private Long id;
    private String eleveMatricule;
    private String eleveNom;
    private String elevePrenom;
    private String eleveAdresse;
    private LocalDate eleveDateNaiss;
    private String eleveLieuNais;
    private String eleveSexe;
    private String eleveEtatProvenance;
    private LocalDate eleveDateIns;
    private String tuteurNom;
    private String tuteurPrenom;
    private String tuteurProfession;
    private String tuteurAdresse;
    private String tuteurTelDom;
    private String tuteurCel;
    private NiveauEtude niveauEtude;
    private Specialite specialite;

    public EleveDto(Long id, String eleveMatricule, String eleveNom, String elevePrenom, String eleveAdresse,
                    LocalDate eleveDateNaiss, String eleveLieuNais, String eleveSexe, String eleveEtatProvenance,
                    LocalDate eleveDateIns, String tuteurNom, String tuteurPrenom, String tuteurProfession,
                    String tuteurAdresse, String tuteurTelDom, String tuteurCel, NiveauEtude niveauEtude,
                    Specialite specialite) {
        this.id = id;
        this.eleveMatricule = eleveMatricule;
        this.eleveNom = eleveNom;
        this.elevePrenom = elevePrenom;
        this.eleveAdresse = eleveAdresse;
        this.eleveDateNaiss = eleveDateNaiss;
        this.eleveLieuNais = eleveLieuNais;
        this.eleveSexe = eleveSexe;
        this.eleveEtatProvenance = eleveEtatProvenance;
        this.eleveDateIns = eleveDateIns;
        this.tuteurNom = tuteurNom;
        this.tuteurPrenom = tuteurPrenom;
        this.tuteurProfession = tuteurProfession;
        this.tuteurAdresse = tuteurAdresse;
        this.tuteurTelDom = tuteurTelDom;
        this.tuteurCel = tuteurCel;
        this.niveauEtude = niveauEtude;
        this.specialite = specialite;
    }

    // Getters et setters (ou utilise @Data de Lombok si nécessaire)


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
