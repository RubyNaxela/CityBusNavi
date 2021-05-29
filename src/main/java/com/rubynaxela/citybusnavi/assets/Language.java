package com.rubynaxela.citybusnavi.assets;

/**
 * Language codes enum
 */
public enum Language {
    ENGLISH_US("en_US"),
    POLISH("pl_PL");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
