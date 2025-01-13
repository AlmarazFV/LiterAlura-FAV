package com.fav.literatura.challenge_literalura_fav.repository;

import com.fav.literatura.challenge_literalura_fav.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento < :anioAutor AND a.fechaFallecido > :anioAutor")
    List<Autor> buscarAutoresVivosPorAnio(int anioAutor);

    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE %:nombreAutor%")
    List<Autor> buscarAutorPorNombre(String nombreAutor);
}
