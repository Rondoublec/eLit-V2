# eLitClient : site web des bibliothèques municipales de Nîmes pour les abonnées
Site web des bibliothèques.

### Contexte  
Ce site est à destination des adhérents, il compose la **V2** du système d'information des bibliothèques municipales d'une ville.  
Il s'agit du 10eme projet du cursus Développeur d'application Java proposé par OpenClassrooms.
L'objet de cette V2 est l'ajout de la gestion des réservations accessibles directement aux usagers pour les ouvrages indisponibles.
Développé par Rémy Bourdoncle.  

### Contenu
Ce site permet aux adhérents de rechercher des ouvrages, de consulter l'état de leurs emprunts et de prolonger leurs emprunts en cours

La **V2** permet de réserver un ouvrage non disponible ainsi que la gestion de ses réservations, consulter l'état de ses réservations, le positionnement de ses réservation dans la liste d'attente, l'annulation de réservations, ...

### Prérequis techniques
Version de Java : 1.8  
JDK : jdk1.8.0_191  
Maven 3.6  

### Documentation
La JavaDoc peut être consultée en lançant le fichier **docs\index.html**  

##Installation et déploiement
Packaging : **mvn clean package**

Aller dans target et lancer le war avec la commande
**java -jar eLiClient-0.0.1-SNAPSHOT.war**
Le port de l'Application est paramétré dans application.propertie  : `http://localhost:8089/`  

**Logs :** Par défaut le niveau de log est positionné à "INFO", les logs sont quotidiens (horodatés) et se trouvent dans le répertoire **logs**, tous ces paramétrages sont dans le fichier **src\main\resources\logback.xml**


Sources disponibles sur : https://github.com/Rondoublec/eLit-V2  
La partie front est sous :  https://github.com/Rondoublec/eLit-V2/tree/master/eLitClient

