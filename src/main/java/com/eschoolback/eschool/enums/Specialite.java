package com.eschoolback.eschool.enums;

public enum Specialite {
    A4("A4"),
    D("D");
//    G1("G1"),
//    G2("G2"),
//    G3("G3");

    private final String libelle;

    Specialite(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static Specialite fromString(String text) {
        for (Specialite specialite : Specialite.values()) {
            if (specialite.libelle.equalsIgnoreCase(text)) {
                return specialite;
            }
        }
        throw new IllegalArgumentException("Spécialité inconnue : " + text);
    }
}
