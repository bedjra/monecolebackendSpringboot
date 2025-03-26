package com.eschoolback.eschool.Dto;

public class NoteDto {

    private Long eleveId;
    private String matiere;
    private float int1;
    private float  int2;
    private Double dev;
    private Double comp;
    private Double moyClasse;
    private Double moyTotal;
    private Double moyCoef;
    private String observation;
    private String professeur;
    private double coefficient;


    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public float getInt1() {
        return int1;
    }

    public void setInt1(float int1) {
        this.int1 = int1;
    }

    public float getInt2() {
        return int2;
    }

    public void setInt2(float int2) {
        this.int2 = int2;
    }

    public Double getDev() {
        return dev;
    }

    public void setDev(Double dev) {
        this.dev = dev;
    }

    public Double getComp() {
        return comp;
    }

    public void setComp(Double comp) {
        this.comp = comp;
    }

    public Double getMoyClasse() {
        return moyClasse;
    }

    public void setMoyClasse(Double moyClasse) {
        this.moyClasse = moyClasse;
    }

    public Double getMoyTotal() {
        return moyTotal;
    }

    public void setMoyTotal(Double moyTotal) {
        this.moyTotal = moyTotal;
    }

    public Double getMoyCoef() {
        return moyCoef;
    }

    public void setMoyCoef(Double moyCoef) {
        this.moyCoef = moyCoef;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(String professeur) {
        this.professeur = professeur;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
