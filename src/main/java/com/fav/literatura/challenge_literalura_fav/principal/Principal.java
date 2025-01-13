package com.fav.literatura.challenge_literalura_fav.principal;

import com.fav.literatura.challenge_literalura_fav.model.*;
import com.fav.literatura.challenge_literalura_fav.service.AutorService;
import com.fav.literatura.challenge_literalura_fav.service.ConsumoAPI;
import com.fav.literatura.challenge_literalura_fav.service.ConvierteDatos;
import com.fav.literatura.challenge_literalura_fav.service.LibroService;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final LibroService libroServicio;
    private final AutorService autorServicio;

    public Principal(LibroService libroService, AutorService autorService) {
        this.libroServicio = libroService;
        this.autorServicio = autorService;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            try {
                String menu = """
                --------------------------------------------------
                **Catálogo de libros en Challenge Literalura**
                1.- Buscar libro por título
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por idioma
                6.- Estadísticas de libros por número de descargas
                7.- Top 10 libros más descargados
                8.- Buscar autor por nombre
                0.- Salir
                ---------------------------------------------------

                Elija la opción a través de su número:""";

                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 -> buscarLibroPorTitulo();
                    case 2 -> listarLibrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> buscarAutoresVivosPorAnio();
                    case 5 -> listarLibrosPorIdioma();
                    case 6 -> estadisticasLibrosPorNumDescargas();
                    case 7 -> top10LibrosMasDescargados();
                    case 8 -> buscarAutorPorNombre();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción inválida. Favor de introducir un número del menú.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Favor de introducir un número del menú.");
                teclado.nextLine();
            }
        }
    }

    private DatosResultados obtenerDatosResultados(String tituloLibro) {
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
        return conversor.obtenerDatos(json, DatosResultados.class);
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Escribe el título del libro que deseas buscar: ");
        var tituloLibro = teclado.nextLine().toUpperCase();
        Optional<Libro> libroRegistrado = libroServicio.buscarLibroPorTitulo(tituloLibro);

        if (libroRegistrado.isPresent()) {
            System.out.println("El libro buscado ya está registrado.");
        } else {
            var datos = obtenerDatosResultados(tituloLibro);
            if (datos.listaLibros().isEmpty()) {
                System.out.println("No se encontró el libro buscado en Gutendex API.");
            } else {
                DatosLibros datosLibros = datos.listaLibros().get(0);
                DatosAutores datosAutores = datosLibros.autores().get(0);
                String idioma = datosLibros.idiomas().get(0);
                Idiomas idiomas = Idiomas.fromString(idioma);
                Libro libro = new Libro(datosLibros);
                libro.setIdiomas(idiomas);
                Optional<Autor> autorRegistrado = autorServicio.buscarAutorRegistrado(datosAutores.nombre());

                if (autorRegistrado.isPresent()) {
                    Autor autorExiste = autorRegistrado.get();
                    libro.setAutor(autorExiste);
                } else {
                    Autor autor = new Autor(datosAutores);
                    autor = autorServicio.guardarAutor(autor);
                    libro.setAutor(autor);
                    autor.getLibros().add(libro);
                }

                try {
                    libroServicio.guardarLibro(libro);
                    System.out.println("\nLibro encontrado.\n");
                    System.out.println(libro + "\n");
                    System.out.println("Libro guardado.\n");
                } catch (DataIntegrityViolationException e) {
                    System.out.println("El libro ya está registrado.");
                }
            }
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroServicio.listarLibrosRegistrados();

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            System.out.println("Los libros registrados son los siguientes:\n");
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorServicio.listarAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
        } else {
            System.out.println("Los autores registrados son los siguientes:\n");
            for (Autor autor : autores) {
                List<Libro> librosPorAutorId = libroServicio.buscarLibrosPorAutorId(autor.getId());
                System.out.println("----- AUTOR -----");
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecido());

                if (librosPorAutorId.isEmpty()) {
                    System.out.println("No se encontraron libros registrados para este autor.");
                } else {
                    String librosRegistrados = librosPorAutorId.stream()
                            .map(Libro::getTitulo)
                            .collect(Collectors.joining(", "));
                    System.out.println("Libros: [" + librosRegistrados + "]");
                    System.out.println("-----------------\n");
                }
            }
        }
    }

    private void buscarAutoresVivosPorAnio() {
        System.out.print("Escribe el año en el que deseas buscar autores vivos: ");
        var anioDelAutor = teclado.nextInt();
        List<Autor> buscarAutoresPorAnio = autorServicio.buscarAutoresVivosPorAnio(anioDelAutor);

        if (buscarAutoresPorAnio.isEmpty()) {
            System.out.println("No se encontraron autores vivos por el año buscado.");
        } else {
            System.out.printf("Autores vivos durante el año %d:\n", anioDelAutor);
            buscarAutoresPorAnio.forEach(autor -> System.out.println(autor.getNombre()));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Estos son los idiomas disponibles:
                - es -> Español
                - en -> Inglés
                - fr -> Francés
                - pt -> Portugués
                """);

        System.out.print("Escribe el idioma abreviado para buscar los libros: ");
        var nombreIdioma = teclado.nextLine();

        try {
            List<Libro> buscarLibrosPorIdioma = libroServicio.buscarLibroPorIdiomas(Idiomas.fromString(nombreIdioma));

            if (buscarLibrosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron los libros del idioma buscado.");
            } else {
                System.out.printf("Los libros del idioma '%s' son los siguientes:\n", nombreIdioma);
                buscarLibrosPorIdioma.forEach(l -> System.out.print(l.toString()));
            }
        } catch (Exception e) {
            System.out.println("Opción inválida. Favor de escribir un idioma abreviado del menú.");
        }
    }

    private void estadisticasLibrosPorNumDescargas() {
        List<Libro> todosLosLibros = libroServicio.listarLibrosRegistrados();

        if (todosLosLibros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            System.out.println("Estadísticas de los libros por número de descargas:\n");
            DoubleSummaryStatistics est = todosLosLibros.stream()
                    .filter(libro -> libro.getNumeroDescargas() > 0)
                    .collect(Collectors.summarizingDouble(Libro::getNumeroDescargas));
            System.out.println("Cantidad media de descargas: " + est.getAverage());
            System.out.println("Cantidad máxima de descargas: " + est.getMax());
            System.out.println("Cantidad mínima de descargas: " + est.getMin());
        }
    }

    private void top10LibrosMasDescargados() {
        List<Libro> top10LibrosMasDescargados = libroServicio.listarTop10LibrosMasDescargados();

        if (top10LibrosMasDescargados.isEmpty()) {
            System.out.println("No se encontraron libros suficientes para mostrar.");
        } else {
            System.out.println("Top 10 libros más descargados:\n");
            top10LibrosMasDescargados.forEach(libro -> System.out.println(libro.toString()));
        }
    }

    private void buscarAutorPorNombre() {
        System.out.print("Escribe el nombre del autor que deseas buscar: ");
        var nombreAutor = teclado.nextLine().toUpperCase();
        List<Autor> autorBuscado = autorServicio.buscarAutorPorNombre(nombreAutor);

        if (autorBuscado.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
        } else {
            System.out.printf("El autor encontrado de nombre '%s' es el siguiente:\n", nombreAutor);
            for (Autor autor : autorBuscado) {
                List<Libro> librosPorAutorId = libroServicio.buscarLibrosPorAutorId(autor.getId());
                System.out.println("----- AUTOR -----");
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecido());

                if (librosPorAutorId.isEmpty()) {
                    System.out.println("No se encontraron libros registrados para este autor.");
                } else {
                    String librosRegistrados = librosPorAutorId.stream()
                            .map(Libro::getTitulo)
                            .collect(Collectors.joining(", "));
                    System.out.println("Libros: [" + librosRegistrados + "]");
                    System.out.println("-----------------\n");
                }
            }
        }
    }
}
