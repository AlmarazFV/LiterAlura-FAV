# LiterAlura - Catálogo de Libros

## 🚀 Descripción del Proyecto

**LiterAlura** es una aplicación interactiva basada en consola que te permite explorar un extenso catálogo de libros gracias a la API de Gutendex, la cual recopila información de más de 70,000 libros del Proyecto Gutenberg. Este proyecto utiliza Java y Spring Boot para brindar una experiencia eficiente y modular.

Con **LiterAlura**, podrás:
- Buscar libros por título.
- Listar autores y libros registrados.
- Consultar estadísticas y datos específicos de libros y autores.

---

## 🛠 Tecnologías Utilizadas

- **Java**: Versión 17 o superior.
- **Spring Boot**: Versión 3.2.3.
- **Maven**: Gestión de dependencias.
- **PostgreSQL**: Base de datos relacional, versión 16 o superior.
- **Jackson**: Para manipulación de datos JSON.

---

## 📋 Características Principales

### 📚 Funcionalidades de Libros:
- **Búsqueda por título**: Encuentra información detallada de libros.
- **Listado de libros registrados**: Muestra todos los libros buscados previamente.
- **Top 10 libros más descargados**: Consulta los títulos más populares.
- **Cantidad de libros por idioma**: Genera estadísticas por idioma.

### ✒️ Funcionalidades de Autores:
- **Listado de autores registrados**: Visualiza información básica de los autores.
- **Consulta de autores vivos**: Filtra autores según un año proporcionado.
- **Búsqueda de autor por nombre**: Encuentra información específica de autores registrados.

---

## 🔧 Configuración del Proyecto

### Requisitos Previos

- **Java JDK**: Versión 17 o superior.
- **Maven**: Versión 4 o superior.
- **PostgreSQL**: Versión 16 o superior.
- **IDE** (opcional): IntelliJ IDEA o cualquier IDE de tu preferencia.

### Configuración Inicial

1. **Clonar el repositorio**:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```

2. **Configurar la base de datos**:
   - Asegúrate de tener PostgreSQL instalado y ejecutándose.
   - Crea una base de datos llamada `liter_alurafav` o la que prefieras.

3. **Configurar el archivo `application.properties`**:
   Ajusta la configuración del archivo `application.properties` para conectar tu base de datos. Aquí tienes un ejemplo:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/liter_alurafav
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CONTRASEÑA
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

---

## 📖 Uso de la Aplicación

1. Al iniciar la aplicación, verás un menú interactivo:

   ```
   --------------------------------------------------
   **Catálogo de libros en LiterAlura**
   1.- Buscar libro por título
   2.- Listar libros registrados
   3.- Listar autores registrados
   4.- Listar autores vivos en un determinado año
   5.- Estadísticas de libros por idioma
   0.- Salir
   ---------------------------------------------------
   ```

2. Selecciona una opción ingresando el número correspondiente y sigue las instrucciones en pantalla.

---

## 🌐 API Gutendex

Este proyecto utiliza la [API Gutendex](https://gutendex.com/) para obtener información sobre libros y autores.

- **URL Base**: `https://gutendex.com/books/`
- No requiere autenticación.
- Consulta su [documentación oficial](https://github.com/garethbjohnson/gutendex) para más detalles.

---

## 🗂 Estructura del Proyecto

- **`model`**: Contiene las clases principales del dominio, como `Libro`, `Autor`, etc.
- **`repository`**: Interfaces para la interacción con la base de datos.
- **`service`**: Lógica del negocio, como la manipulación de datos y el consumo de la API.
- **`principal`**: Clase principal con el menú interactivo.

---

## 🌟 Funcionalidades Adicionales

- **Generar estadísticas avanzadas**: Calcula promedio, máximo y mínimo de descargas con `DoubleSummaryStatistics`.
- **Buscar autor por nombre**: Consulta optimizada en la base de datos.
- **Top 10 libros más descargados**: Implementado con Streams y consultas personalizadas.

---

## 🤝 Contribución

1. Haz un fork del repositorio.
2. Crea una rama nueva para tus cambios:
   ```bash
   git checkout -b mi-nueva-funcionalidad
   ```
3. Realiza un commit con tus cambios:
   ```bash
   git commit -m "Agregué una nueva funcionalidad"
   ```
4. Envía tus cambios al repositorio remoto:
   ```bash
   git push origin mi-nueva-funcionalidad
   ```
5. Crea un pull request en este repositorio.

---

## 👤 Autor

- **Fernando Almaraz Vences**
- [GitHub](https://github.com/AlmarazFV)

---

## 📝 Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más información.
