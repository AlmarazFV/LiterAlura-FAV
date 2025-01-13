package com.fav.literatura.challenge_literalura_fav.model;

public enum Idiomas {
    // inglés
    en("en"),
    // español
    es("es"),
    // francés
    fr("fr"),
    // húngaro
    hu("hu"),
    // finés
    fi("fi"),
    // portugués
    pt("pt");

    private final String idiomasGutendex;

    Idiomas(String idiomasGutendex) {
        this.idiomasGutendex = idiomasGutendex;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomasGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún idioma: " + text);
    }
}
