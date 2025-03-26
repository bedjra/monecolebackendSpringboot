package com.eschoolback.eschool.enums;

public enum Observation {
    TRES_BIEN("Très bien", 16.0, 20.0),
    BIEN("Bien", 14.0, 15.99),
    ASSEZ_BIEN("Assez bien", 12.0, 13.99),
    PASSABLE("Passable", 10.0, 11.99),
    INSUFFISANT("Insuffisant", 8.0, 9.99),
    AVERTISSEMENT("Avertissement", 5.0, 7.99),
    MISE_EN_GARDE("Mise en garde", 0.0, 4.99); // Ajout d'un cas pour éviter les valeurs négatives

    private final String label;
    private final double min;
    private final double max;

    Observation(String label, double min, double max) {
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public String getLabel() {
        return label;
    }

    public static Observation getObservationFromMoyTotal(double moyTotal) {
        for (Observation obs : values()) {
            if (moyTotal >= obs.min && moyTotal <= obs.max) {
                return obs;
            }
        }
        return INSUFFISANT; // Valeur par défaut pour éviter une erreur si la moyenne ne correspond pas
    }
}

