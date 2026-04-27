# League Manager

Aplicacion de escritorio en Java para gestionar ligas, equipos, jugadores y partidos.

El repositorio incluye un `pom.xml` para que las dependencias se resuelvan con Maven en lugar de depender de la configuracion local de librerias en IntelliJ.

## Tecnologias usadas

- Java 8+
- Swing para la interfaz de escritorio
- MySQL para la persistencia
- Maven para la gestion de dependencias
- Gson y `org.json` para trabajar con JSON

## Estructura actual del repositorio

```text
.
|-- src/          Codigo fuente Java
|-- data/         Archivos JSON de ejemplo para equipos
|-- dataconfig/   Configuracion local de base de datos
|-- photos/       Imagenes de la interfaz
|-- pom.xml       Build y dependencias con Maven
|-- README.md
```

## Requisitos

- JDK 8 o superior
- Maven 3.9 o superior
- MySQL ejecutandose en local, o XAMPP con MySQL activado

## Configuracion de la base de datos

La aplicacion lee la configuracion de conexion desde `dataconfig/config.Json`.

Valores por defecto actuales:

```json
{
  "databasePort": "3306",
  "databaseIP": "localhost",
  "databaseName": "leaguemanager",
  "databaseUser": "root",
  "databasePassword": "",
  "admin": "12345678A",
  "adminPassword": "admin1",
  "matchTime": "1"
}
```

## Funcionalidades principales

- Flujos de login, registro y perfil de usuario
- Creacion de equipos a partir de archivos JSON en `data/teams`
- Pantallas de gestion de ligas y partidos
