package com.fav.literatura.challenge_literalura_fav.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
