# Sistemas informaticos 💻

## 1. Instalación y configuración.

### Instalación de PostgreSQL y pgAdmin:

1. **PostgreSQL**:

- Descargamos el instalador desde [postgresql.org](https://www.postgresql.org/download/). Eligiendo el sistema operativo y la version que queramos
 <img width="60" height="60" alt="image" src="https://github.com/user-attachments/assets/1e688ea1-c48c-4469-9c74-7c20cad9e092" />


 - Una vez registrados en pgAdmin, usando el puerto predeterminado(5432) y la contraseña, crearemos la database, dandole click derecho a database, newDatabase

   <img width="514" height="112" alt="image" src="https://github.com/user-attachments/assets/870cbd2b-60e1-47f6-9ec1-bc30f49d4820" />


- Una vez dentro, debemos añadirle un nombre y darle a save para confirmar el nombre y la creacion de la base de datos.

  <img width="502" height="349" alt="image" src="https://github.com/user-attachments/assets/b94119c0-7fb0-4034-aa66-a9c698a9e8b2" />


- Una vez creada la base de datos,le damos click derecho a la carpeta y pinchamos en query tool
  
  <img width="321" height="444" alt="image" src="https://github.com/user-attachments/assets/3f7fb57e-5780-47bf-9675-fb9cf67ff63b" />

- Una vez dentro en la parte del backend, resources, hay dos archivos DDL y DML, con ellos debemos descargar el archivo, y dentro de pgAdmin debemos darle a openFile
  <img width="870" height="286" alt="image" src="https://github.com/user-attachments/assets/0afa03c8-a266-4073-b46e-2fddd2c438c2" />

- Primero abriremos el DML y le daremos al F5 para crear las tablas, y posteriormente al DDL para crear los inserts.

- Con estos pasos, podremos tener na base de datos funcional, Importante, no debemos salir del pgAdmin si queremos que funcione, sino, se perdera la conexion con la base de datos.



### Instalación de IntelliJ IDEA (IJ).

- Primero deberemos instalar el IntelliJ con este enlace [jetbrains.com/idea](https://www.jetbrains.com/idea/download/)

  <img width="70" height="60" alt="image" src="https://github.com/user-attachments/assets/09bc00e9-c6ec-410c-a5f4-f26e3895c87e" />

- Una vez instalado necesitaremos darle a clone repository

  <img width="265" height="98" alt="image" src="https://github.com/user-attachments/assets/d1c69e2d-eadd-4394-8b74-a23a8d154aab" />

- Nos vamos al repositorio de github que tenemos y en el apartado de `code` se copia y se pega en la url y pòsteriormente le damos a clone.

  <img width="605" height="597" alt="image" src="https://github.com/user-attachments/assets/6d193765-8c58-4693-b708-0c148dfe0d30" />

- Ya dentro buscamos en la carpeta del backend, database config, y cambiamos nuestras credenciales
  
  <img width="1223" height="332" alt="image" src="https://github.com/user-attachments/assets/22d71ba9-b5db-444d-822d-a790d1f245bf" />

- Finalmente nos iremos al Main y le daremos a play, el icono verde que hay arriba a la derecha del codigo.

  <img width="1006" height="576" alt="image" src="https://github.com/user-attachments/assets/3a600635-9304-4ea4-878f-0035a72dbf15" />

- Esto nos deberia de funcionar.

  <img width="1134" height="123" alt="image" src="https://github.com/user-attachments/assets/2c5312a7-a4af-4fe4-9806-ba6bc16b62b8" />

- Importante, como en la base de datos, esto no lo podremos parar, ya que sino no funcionara a pagina web



### Instalacion de Visual Studio / VS Code.

- Debemos instalar Visual Studio con el siguiente enlace: [code.visualstudio.com](https://code.visualstudio.com/)

  <img width="225" height="155" alt="image" src="https://github.com/user-attachments/assets/82bfc618-f74e-419e-acd8-f3fcdd0d04c2" />


- Una vez instalado y dentro en la parte superior del programa ponemos lo siguiente:

<img width="601" height="100" alt="image" src="https://github.com/user-attachments/assets/b469d13c-1406-403f-bb14-b6228b7dfd7a" />


- Despues de haberlo clonado necesitamos instalarnos una extension, se pueden descargar desde la parte izquierda del programa, llamado live Server.

  <img width="768" height="184" alt="image" src="https://github.com/user-attachments/assets/b3f69981-49ca-4475-8921-bb137eae8080" />

- Finalmente entramos en home.html que esta en la carpeta de frontend y ejecutamos el programa dandole abajo a la derecha al live server

  <img width="82" height="23" alt="image" src="https://github.com/user-attachments/assets/b8bd8026-8b64-4bb7-8ee7-16b77392dd71" />



 **Con todo esto seremos capaces de hacer que el programa funcione**



## 2. Descripción del entorno necesario.

Este proyecto esta desarrollado bajo un entorno tecnológico, dividido en tres capas principales: **frontend**, **backend** y **base de datos**, cada una con sus herramientas especificas que cumplen las funciones clave en el desarrollo del sistema.

### - Frontend (Interfaz del usuario).

Es la parte visual del sistema, en la que el usuario interactua directamente. Para organizar esta parte del proyecto se utilizó Visual Studio Code, con la extension de `Live Server`, facilitando el diseño y prueba de interfaces.

### - Backend

Es la parte invisible del sistema. Responsable de procesar los datos y comunicarse con la base de datos, entre otras cosas. Fue desarrollado en Java mediante IntelliJ (la version gratuita), con el `openjdk-25` y usando una dependencia `maven` permitiendo desarrollar las aplicaciones con mayor claridad.

### - Base de datos

Son todos los datos del sistema y donde se almacenan los datos introducidos en la web, utilizando **pgAdmin**, siendo una herramienta grafica que crea y modifica la base de datos sin necesidad de crear comandos complejos. Es necesario crear un servidor usando `Localhost` e introduciendo un *usuario* y una *contraseña*.

### - Control de versiones

El codigo del proyecto se gestiona mediante **GitHub**, una plataforma que permite colaborar con otros desarrolladores y seguir sus cambios realizados. Para poder usar dicha plataforma, es necesario tener una **cuenta** creada, una vez hecho eso, se podra crear un *repositorio* e invitar a `colavoradores` para que participen en dicho proyecto.



## 3. Justificacion de las herramientas utilizadas.

### PostgreSQL:

Es un `SGBD` (*sistema de gestion de base de datos*) relacional potente, *open-source*, con soporte para tipos de datos avanzados y gran escabilidad. Ideal para aplicaciones robustas y seguras.

### pgAdmin:

Herramienta oficial de `PostgreSQL` con una interfaz grafica intuitiva. Permitiendo **gestionar** la base de datos, ejecutar **consultas** y administrar la base de datos de forma sencilla.

### IntelliJ IDEA:

Esta considerado uno de los mejores IDEs para Java. Ofrece una depuración avanzada, integra maven, a parte del proyecto, tambien tiene un soporte de frameworks como Spring. Aumentando la productividad del que desarrolle el programa.

### Visual Studio Code:

Lígero y muy **personalizable**. Con miles de **extensiones**, siendo muy usado en el `forntend` para la mayoria de desarroladores. Tambien, soporta multiples lenguajes y permite **previsualizar** los cambios en tiempo real.

### GitHub:

Es la plataforma mas usada para el control de versiones. Facilita la **colaboracion** entre desarrolladores y la **revision** de codigo. Es esencial para **proyectos** en equipo o mantenimiento a largo plazo.
