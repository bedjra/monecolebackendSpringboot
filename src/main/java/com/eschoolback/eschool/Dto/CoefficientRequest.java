package com.eschoolback.eschool.Dto;


import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import java.util.List;

public class CoefficientRequest {
    private NiveauEtude niveauEtude;
    private Specialite specialite;
    private List<MatiereCoefficient> coefficients;

    // Getters et Setters
    public NiveauEtude getNiveauEtude() { return niveauEtude; }
    public void setNiveauEtude(NiveauEtude niveauEtude) { this.niveauEtude = niveauEtude; }

    public Specialite getSpecialite() { return specialite; }
    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }

    public List<MatiereCoefficient> getCoefficients() { return coefficients; }
    public void setCoefficients(List<MatiereCoefficient> coefficients) { this.coefficients = coefficients; }

    // Classe interne pour gérer chaque matière et son coefficient
    public static class MatiereCoefficient {
        private String matiere;
        private double coefficient;

        public String getMatiere() { return matiere; }
        public void setMatiere(String matiere) { this.matiere = matiere; }

        public double getCoefficient() { return coefficient; }
        public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
    }
}
