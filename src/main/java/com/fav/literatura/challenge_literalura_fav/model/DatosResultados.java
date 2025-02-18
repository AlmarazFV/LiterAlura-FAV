package com.fav.literatura.challenge_literalura_fav.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultados(

        @JsonAlias("results") List<DatosLibros> listaLibros

) {
}
