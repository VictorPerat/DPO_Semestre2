——— LEAGUE MANAGER———

Descripció del Projecte:

League Manager és una aplicació creada per gestionar competicions esportives. El projecte està desenvolupat en Java i segueix una arquitectura per capes. Inclou la lògica necessària per administrar equips, jugadors, partits i resultats. També permet guardar tota la informació en una base de dades, incloent dades específiques de les lligues, equips i jugadors.

Estructura i Carpetes del Projecte:
	•	src/: Carpeta principal del codi font. Aquí es troba la lògica del programa.
	•	out/: Carpeta de sortida del projecte.
	•	.idea/: Carpeta amb la configuració del projecte i les llibreries importades.
	•	business/: Conté la capa de negoci.
	•	persistance/: Inclou la capa de persistència, arxius i connexió amb la base de dades.
	•	presentation/: Conté la capa de presentació de la interfície.
	•	ControllerViews/: Controladors que interactuen amb la capa de presentació.
	•	Rounded/: Carpeta on es troven els botons de les pantalles arrodonits. 
	•	views/: Finestres amb les diferents funcionalitats de l’aplicació.
	•	data/: Accés a dades, Json dels equips.
	•	BaseDades/: Carpeta on es troba el Script SQL per crear i configurar la base de dades.

Requisits:
	•	IntelliJ IDEA: Es recomana fer servir IntelliJ IDEA per desenvolupar i executar el projecte.
	•	SDK 21+: Assegura’t de tenir instal·lat el SDK 21 o superior.
	•	PhpMyAdmin: La base de dades utilitzada és phpMyAdmin. Cal tenir el XAMPP instal·lat. 

Instruccions d’Instal·lació:
	1.	Obre IntelliJ i importa la carpeta principal del projecte, anomenada "S2_Project-E-LeagueManager-2”.
	2.	Un cop dins del projecte, a la part superior, a l’apartat “VCS”, prem el botó “Create Git Repository”.
	3.	Desplega el panell principal, cerca l’opció de Git i prem el botó “Clone”.
	4.	A la finestra de “Clone”, enganxa l’enllaç del repositori i prem “Clone”.
	5.	Un cop clonat correctament, començaràs la configuració i importació de la base de dades.
	6.	Per això, descarrega’t el paquet XAMPP, amb MySQL, Apache i phpMyAdmin inclosos.
	7.	Un cop instal·lat, obre XAMPP i inicia els serveis d’Apache i MySQL.
	8.	Si tot s’ha iniciat correctament, prem el botó “admin” de la fila de “MySQL”.
	9.	Se t’obrirà una pàgina web local on podràs crear una nova base de dades prement el botó “Nueva”.
	10.	Escriu un nom com, per exemple, “projecte_dpo” i prem “Crear”.
	11.	Després, a la pestanya superior “Importar”, selecciona l’arxiu projecte_dpo.sql des de la carpeta principal del projecte.
	12.	Torna al projecte i, dins la carpeta /rsc, localitza la classe Main i executa-la.
	13.	Si has seguit correctament tots els passos, hauries de tenir la base de dades configurada en local.
	14.	Prem el botó d’execució de la classe Main dins l’opció “Run” per iniciar l’aplicació.

Ús de l’Aplicació:

Quan l’aplicació estigui en marxa, podràs accedir a totes les funcionalitats mitjançant la interfície gràfica.

L’aplicació permet:
	•	Gestionar equips i consultar els jugadors (si tens rol d’administrador).
	•	Planificar i registrar partits.
	•	Crear, consultar i esborrar lligues.
	•	Visualitzar estadístiques i gràfics per jornades.
	•	Veure la simulació d’un partit en temps real.
	•	Consultar partits i lligues en què participes com a jugador.

