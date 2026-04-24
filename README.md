# Marketplace de Coches - Proyecto Intermodular

Este proyecto es una plataforma de compraventa de coches, desarrollada como proyecto intermodular.

## Stack Tecnológico

- **Backend:** Java 21 con `HttpServer`.
- **Base de Datos:** PostgreSQL.
- **Frontend:** HTML5, CSS3 y JavaScript con `fetch` API.
- **Persistencia:** JDBC.
- **Testing:** JUnit 5.

## Requisitos Previos

- Java 21 instalado.
- Maven 3.x.
- PostgreSQL ejecutándose en el puerto 5432.
- Base de datos llamada `intermodular` con usuario/password `postgres`/`postgres`.

## Instalación y Ejecución

1. **Base de Datos:**
   Ejecuta los scripts SQL en orden:
   - `src/main/resources/DDL.sql`
   - `src/main/resources/DML.sql`

2. **Compilar y Ejecutar:**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

3. **Acceso:**
   - Aplicación: [http://localhost:8080/](http://localhost:8080/)
   - Documentación API: [http://localhost:8080/api/docs](http://localhost:8080/api/docs)

## Estructura del Proyecto

- `src/main/java/org.example/model`: POJOs de las entidades.
- `src/main/java/org.example/dao`: Capa de acceso a datos (SQL puro).
- `src/main/java/org.example/service`: Lógica de negocio.
- `src/main/java/org.example/router`: Servidor y manejo de rutas.
- `frontend`: Frontend (HTML/JS).

## Autores
- Luis Lopez-Nuño Sánchez
- Daniel Nieto Ladino
- Julio Martín Rodríguez Sánchez
&copy; 2026 · Proyecto Intermodular
