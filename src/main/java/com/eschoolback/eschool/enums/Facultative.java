package com.eschoolback.eschool.enums;

public enum Facultative {
    EPS("EPS"),
    MUSIQUE("Musique");

    private final String displayName;

    Facultative(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
