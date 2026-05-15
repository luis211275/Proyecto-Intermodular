# 🏎️BSCars

## Compra y venta de coches

Proyecto de primero de DAW sobre una web de compra y venta de coches.

La idea del proyecto es que un usuario pueda registrarse, iniciar sesion, ver coches del catalogo y consultar informacion de cada vehiculo. Tambien hemos trabajado la parte de backend con Java y la conexion con base de datos.

## Que incluye

- Registro e inicio de sesion de usuarios
- Catalogo de coches
- Busqueda de vehiculos
- Publicacion de coches
- Paginas de compra y venta
- Conexion con base de datos PostgreSQL

## Tecnologias

- Frontend: HTML, CSS y JavaScript
- Backend: Java
- Base de datos: PostgreSQL
- Gestion del proyecto: Maven
- Tests: JUnit y Mockito

## Estructura del proyecto

- `frontend/html`: paginas de la web
- `frontend/css`: estilos
- `frontend/js`: scripts del frontend
- `frontend/assets`: imagenes y recursos
- `backend/src/main/java`: codigo Java
- `backend/src/main/resources`: scripts SQL y documentacion al estilo OpenAPI
- `backend/src/test/java`: tests
- `docs`: documentacion de las asignaturas Empleabilidad y Sistemas Informáticos

## Como ejecutar el proyecto

Antes de empezar necesitas tener instalado:

- Java 21
- Maven
- PostgreSQL

Pasos:

1. Crear una base de datos en PostgreSQL llamada `intermodular`.
2. Ejecutar los scripts `backend/src/main/resources/DDL.sql` y `backend/src/main/resources/DML.sql`.
3. Revisar la configuracion de la base de datos en [DatabaseConfig.java]
4. Iniciar el proyecto con:

```bash
mvn clean compile exec:java
```

5. Abrir en el navegador:

```text
http://localhost:8080/home.html
```

## Documentacion

- API: `http://localhost:8080/api/docs`
- OpenAPI: `backend/src/main/resources/openapi.yaml`

## Autores

- Luis Lopez-Nuño Sanchez
- Daniel Nieto Ladino
- Julio Martin Rodriguez Sanchez
