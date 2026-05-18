# League Manager

Aplicacion de escritorio en Java para gestionar ligas, equipos, jugadores y partidos.

## Requisitos

- JDK 8 o superior. El proyecto esta configurado en Maven con `source`, `target` y `release` a `8`.
- Maven 3.9 o superior.
- MySQL en local, o XAMPP con MySQL activado.

## Version de Java recomendada

Puedes usar cualquier JDK compatible con Java 8. Si quieres ajustarte exactamente a lo que declara el proyecto, usa JDK 8.

## Estructura del repositorio

```text
.
|-- src/          Codigo fuente Java
|-- data/         Archivos JSON de ejemplo para equipos
|-- dataconfig/   Configuracion local de base de datos y admin
|-- photos/       Imagenes de la interfaz
|-- javadoc/      JavaDoc generado
|-- doc/          Copia adicional del JavaDoc
|-- pom.xml       Build y dependencias con Maven
|-- README.md
```

## Como crear o importar la base de datos

1. Inicia MySQL.
2. Crea una base de datos llamada `leaguemanager`.
3. Configura en `dataconfig/config.Json` el host, puerto, usuario y contrasena que correspondan a tu instalacion.
4. Importa el esquema y los datos si dispones de un dump SQL externo.

Ejemplo minimo para crear la base:

```sql
CREATE DATABASE leaguemanager
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Importante:

- Este repositorio no incluye un archivo `.sql` de esquema o de datos.
- La aplicacion no crea las tablas desde cero al arrancar.
- La app espera que la base ya tenga, como minimo, las tablas `players`, `teams`, `player_teams`, `leagues`, `league_teams` y `games`.
- Al iniciar, la app solo intenta aplicar dos ajustes menores de esquema sobre una base ya existente.

## Como configurar `config.Json`

El archivo real del proyecto esta en `dataconfig/config.Json`.

Contenido actual:

```json
{
  "databasePort": "3306",
  "databaseIP": "localhost",
  "databaseName": "leaguemanager",
  "databaseUser": "root",
  "databasePassword": "",
  "admin": "admin",
  "adminPassword": "Hisd02061%",
  "matchTime": "1"
}
```

Significado de cada campo:

- `databasePort`: puerto de MySQL.
- `databaseIP`: host o IP del servidor MySQL.
- `databaseName`: nombre de la base de datos.
- `databaseUser`: usuario de MySQL.
- `databasePassword`: contrasena de MySQL.
- `admin`: identificador del usuario administrador.
- `adminPassword`: contrasena del administrador.
- `matchTime`: duracion de cada partido en minutos.

## Como ejecutar el programa

Desde la raiz del proyecto:

```bash
mvn clean compile
mvn exec:java
```

Tambien puedes importarlo como proyecto Maven en IntelliJ y ejecutar la clase `Main`.

Notas:

- Es recomendable arrancarlo desde la raiz del repositorio para que encuentre bien `dataconfig/`, `data/` y `photos/`.
- Si MySQL no esta disponible o `config.Json` apunta mal, la app abre la vista de error de base de datos.

## Usuario admin y contrasena

Con la configuracion actual de `dataconfig/config.Json`:

- Usuario admin: `admin`
- Contrasena admin: `Hisd02061%`

Si cambias `admin` o `adminPassword` en el JSON, cambiaran tambien estas credenciales de acceso.

## Donde esta la memoria

No hay ningun archivo de memoria identificado dentro de este repositorio.

El unico PDF incluido actualmente es:

- `DPOO-LeagueManager-specs (2).pdf`

## Donde esta el JavaDoc

La ruta principal es:

- `javadoc/index.html`

Tambien hay una copia adicional en:

- `doc/index.html`

## Que JSON de prueba se puede usar

Puedes usar cualquiera de los JSON de `data/teams`. Algunos ejemplos:

- `data/teams/Nankatsu.json`
- `data/teams/Toho.json`
- `data/teams/Titanes.json`
- `data/teams/Furano.json`
- `data/teams/Meiwa.json`
- `data/teams/Newpi.json`
- `data/teams/Montcada Alope.json`
- `data/teams/Dragones.json`
- `data/teams/InstitutRaimon.json`

Esos archivos se pueden importar desde la pantalla de creacion de equipos.
