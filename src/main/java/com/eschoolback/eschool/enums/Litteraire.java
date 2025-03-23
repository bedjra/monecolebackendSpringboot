package com.eschoolback.eschool.enums;

public enum Litteraire {
    ALLEMAND("Allemand"),
    ANGLAIS("Anglais"),
    FRANCAIS("Français"),
    ESPAGNOL("Espagnol"),
    ECM("ECM"),
    HISTOIRE_GEOGRAPHIE("Histoire-Géographie");

    private final String displayName;

    Litteraire(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
