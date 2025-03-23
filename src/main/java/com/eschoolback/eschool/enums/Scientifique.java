package com.eschoolback.eschool.enums;

public enum Scientifique {
    MATHEMATIQUES("Mathématiques"),
    PHYSIQUE_CHIMIE("Physique-Chimie"),
    SVT("SVT");

    private final String displayName;

    Scientifique(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
