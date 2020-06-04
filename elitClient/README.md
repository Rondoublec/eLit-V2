# eLitClient : site web des bibliothèques municipales de Nîmes pour les abonnées
Site web des bibliothèques.

### Contexte  
Ce site est à destination des adhérents, il compose la V1 du système d'information des bibliothèques municipales d'une ville.  
Il s'agit du 7eme projet du cursus Développeur d'application Java proposé par OpenClassrooms.
Développé par Rémy Bourdoncle.  

### Contenu
Ce site permet aux adhérents de rechercher des ouvrages, de consulter l'état de leurs emprunts et de prolonger leurs emprunts en cours

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


Sources disponibles sur : https://github.com/Rondoublec/eLit-V1  
La partie front est sous :  https://github.com/Rondoublec/eLit-V1/tree/master/eLitClient

