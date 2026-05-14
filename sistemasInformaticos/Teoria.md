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

- Una vez dentro 





- Ejecutar el instalador y seguimos los pasos:
    - Seleccionamos los componentes (PostgreSQL, pgAdmin, etc...).
    - Elige el puerto predeterminado (5432).
    - Establece una contraseña para el usuario `postgres`.
- Al finalizar, se abrirá una ventana para configurar pgAdmin.


2. **pgAdmin**:

    - Durante la instalación de PostgreSQL, se instala automáticamente pgAdmin.
    - Accede a través del navegador en `http://localhost:8080`.
    - Inicia sesión con el correo y contraseña definidos durante la instalación.
    - Conéctate al servidor PostgreSQL usando el host `localhost`, usuario `postgres` y la contraseña establecida.


### Instalación de IntelliJ IDEA (IJ).

1. Descarga IntelliJ IDEA Community o Ultimate desde [jetbrains.com/idea](https://www.jetbrains.com/idea/download/).
2. Ejecuta el instalador y sigue las instrucciones.
3. Al abrirlo por primera vez:
    - Configura el `JDK` (En este proyecto se ha usado openkdj-25).
    - Abre o crea un proyecto de java y configura las dependencias(IntelliJ/Maven/Gradle). En este caso se ha usado `Maven`.

### Instalacion de Visual Studio / VS Code.

- Descarga desde [code.visualstudio.com](https://code.visualstudio.com/).   
- Instálalo y abre o crea un proyecto.
- Si es necessario, instala extensiones utiles en uno de los iconos de la izquierda: *Live Server* o *Prettier* son algunas de las que se han usado para este proyecto.
- Abre la carpeta del proyecto y crea nuevos folders acabando con *.html*, *.css* o *.js*.
- Finalmente, usa la extension de `Live Server` para previsualizar la pagina web localmente.

### GitHub.

- Registrese o inicie sesión en GitHub y posteriormente crea un nuevo repositorio en [github.com](https://github.com).
- Clona el repositorio localmente:
```bash
  git clone https://github.com/usuario/proyecto.git
  ```




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
