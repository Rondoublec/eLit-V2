# eLitAPI : API des bibliothèques municipales de Nîmes
API de gestion de bibliothèques.

### Contexte  
Cette API compose la V1 du système d'information des bibliothèques municipales d'une ville.  
Il s'agit du 7eme projet du cursus Développeur d'application Java proposé par OpenClassrooms.
Développé par Rémy Bourdoncle.  

### Contenu
Cette API gère les comptes utilisateurs, les ouvrages et les emprunts

### Prérequis techniques 
Version de Java : 1.8  
JDK : jdk1.8.0_191  
Maven 3.6  
### Base de données  
PostgresSQL

### Documentation
http://localhost:8088/swagger-ui.html  
La JavaDoc peut être consultée en lançant le fichier **docs\index.html**  

##Installation et déploiement
Packaging : **mvn clean package**

Aller dans target et lancer le war avec la commande
**java -jar eLitAPI-0.0.1-SNAPSHOT.war**

Le port de l'Application est paramétré dans application.propertie  : `http://localhost:8088/`  
Par défaut mode **dev** (base mémoire H2), la base est peuplée avec un jeu de données de tests.  
  
L'application est livrée avec 2 configurations   
- **dev** avec une base de données en mémoire (H2) créée à chaque lancement et peuplée avec le contenu du script src\resources\data.sql.  
 Les mots de passes sont dans le data.sql  
- **prod** avec une base de données PostgreSQL à peupler. Lors du 1er lancement pour créer le modèle il faut mettre à **creat-drop** le paramètre spring.jpa.hibernate.ddl-auto dans le fichier application.properties correspondant.
Pour conserver le contenu aux lancements suivants positionnez à **update** la valeur de ce paramètre.
*Vous devez tout de même au préalable avoir créé la base de données avec son compte de propriétaire -> ces informations seront à mettre à jour dans le fichier application.properties correspondant à la configuration de l'application.*
- Les durées standards d'emprunt et de prolongation sont paramétrées dans le **application.properties** (par défaut 28 jours)


**Logs :** Par défaut le niveau de log est positionné à "INFO", les logs sont quotidiens (horodatés) et se trouvent dans le répertoire **logs**, tous ces paramétrages sont dans le fichier **src\main\resources\logback.xml**

Sources disponibles sur : https://github.com/Rondoublec/eLit-V1  
La partie API est sous :  https://github.com/Rondoublec/eLit-V1/tree/master/elitAPI

