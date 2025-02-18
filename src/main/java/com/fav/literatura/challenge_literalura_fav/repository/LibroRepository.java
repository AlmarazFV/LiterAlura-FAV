package com.fav.literatura.challenge_literalura_fav.repository;

import com.fav.literatura.challenge_literalura_fav.model.Idiomas;
import com.fav.literatura.challenge_literalura_fav.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :autorId")
    List<Libro> buscarLibrosPorAutorId(Long autorId);

    List<Libro> findByIdiomas(Idiomas nombreIdioma);

    @Query(value = "SELECT * FROM libros ORDER BY numero_descargas DESC LIMIT 10;", nativeQuery = true)
    List<Libro> top10LibrosMasDescargados();
}
