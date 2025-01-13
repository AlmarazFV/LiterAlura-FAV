package com.fav.literatura.challenge_literalura_fav.service;

import com.fav.literatura.challenge_literalura_fav.model.Autor;
import com.fav.literatura.challenge_literalura_fav.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepositorio;

    public Optional<Autor> buscarAutorRegistrado(String nombre) {
        return autorRepositorio.findByNombreIgnoreCase(nombre);
    }

    public Autor guardarAutor(Autor autor) {
        return autorRepositorio.save(autor);
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepositorio.findAll();
    }

    public List<Autor> buscarAutoresVivosPorAnio(int autorAnio) {
        return autorRepositorio.buscarAutoresVivosPorAnio(autorAnio);
    }

    public List<Autor> buscarAutorPorNombre(String nombreAutor) {
        return autorRepositorio.buscarAutorPorNombre(nombreAutor);
    }
}
