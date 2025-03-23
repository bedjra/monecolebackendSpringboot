package com.eschoolback.eschool.Dto;

import java.util.List;

public class MatiereDTO {
    private String nom;
    private String categorie;
    private List<String> professeurs;

    public MatiereDTO(String nom, String categorie, List<String> professeurs) {
        this.nom = nom;
        this.categorie = categorie;
        this.professeurs = professeurs;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public List<String> getProfesseurs() {
        return professeurs;
    }
}
