# Sistemas informaticos

## 1. Instalación y configuración 💻

### Instalación de PostgreSQL y pgAdmin:

1. **PostgreSQL**:

- Descargamos el instalador desde [postgresql.org](https://www.postgresql.org/download/).

- Ejecutar el instalador y seguimos los pasos:
    - Seleccionamos los componentes (PostgreSQL, pgAdmin, etc...).
    - Elige el puerto predeterminado (5432).
    - Establece una contraseña para el usuario `postgres`.
- Al finalizar, se abrirá una ventana para configurar pgAdmin.


2. **pgAdmin**:

    - Durante la instalación de PostgreSQL, se instala automáticamente pgAdmin.
    - Accede a través del navegador en `http://localhost:5050`.
    - Inicia sesión con el correo y contraseña definidos durante la instalación.
    - Conéctate al servidor PostgreSQL usando el host `localhost`, usuario `postgres` y la contraseña establecida.


### Instalación de IntelliJ IDEA (IJ).

1. Descarga IntelliJ IDEA Community o Ultimate desde [jetbrains.com/idea](https://www.jetbrains.com/idea/download/).
2. Ejecuta el instalador y sigue las instrucciones
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



2. ## Descripción del entorno necesario