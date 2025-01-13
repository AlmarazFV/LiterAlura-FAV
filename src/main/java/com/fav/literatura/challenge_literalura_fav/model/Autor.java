package com.fav.literatura.challenge_literalura_fav.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer fechaNacimiento;

    private Integer fechaFallecido;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.fechaNacimiento = datosAutores.fechaNacimiento();
        this.fechaFallecido = datosAutores.fechaFallecido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecido() {
        return fechaFallecido;
    }

    public void setFechaFallecido(Integer fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    public List<Libro> getLibros() {
        if (libros == null) {
            libros = new ArrayList<>();
        }
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "----- Autores -----" + '\n' +
                "Nombre: " + nombre + '\n' +
                "Fecha de Nacimiento: " + fechaNacimiento + '\n' +
                "Fecha de Fallecimiento: " + fechaFallecido  + '\n' +
                "-------------------" + '\n';
    }
}
