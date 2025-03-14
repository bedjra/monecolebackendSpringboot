package com.eschoolback.eschool.Dto;

import com.eschoolback.eschool.enums.NiveauEtude;

public class StatistiquesDTO {
    private NiveauEtude niveauEtude;
    private Long nbTotalEleves;
    private Long nbFilles;
    private Long nbGarcons;

    public StatistiquesDTO(NiveauEtude niveauEtude, Long nbTotalEleves, Long nbFilles, Long nbGarcons) {
        this.niveauEtude = niveauEtude;
        this.nbTotalEleves = nbTotalEleves;
        this.nbFilles = nbFilles;
        this.nbGarcons = nbGarcons;
    }

    // Getters et Setters


    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Long getNbTotalEleves() {
        return nbTotalEleves;
    }

    public void setNbTotalEleves(Long nbTotalEleves) {
        this.nbTotalEleves = nbTotalEleves;
    }

    public Long getNbFilles() {
        return nbFilles;
    }

    public void setNbFilles(Long nbFilles) {
        this.nbFilles = nbFilles;
    }

    public Long getNbGarcons() {
        return nbGarcons;
    }

    public void setNbGarcons(Long nbGarcons) {
        this.nbGarcons = nbGarcons;
    }
}
