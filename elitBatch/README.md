# eLitBatch : batch de traitement des relances pour les retards de restitution des ouvrages empruntés

### Contexte  
Cette application est à destination des adhérents, elle compose la **V2** du système d'information des bibliothèques municipales d'une ville.  
Il s'agit du 7eme projet du cursus Développeur d'application Java proposé par OpenClassrooms.
L'objet de cette V2 est l'ajout de la gestion des réservations accessibles directement aux usagers pour les ouvrages indisponibles.
Développé par Rémy Bourdoncle. 

### Contenu
A une fréquence déterminée.  
Ce traitement recherche les ouvrages non restitués par adhérent dont la date de restitution est dépassée.  
Et envoi à chaque adhérent concerné un eMail lui rappelant la liste des ouvrages pour lesquels il est en retard de restitution.  

La **V2** ajoute la notification par mail de disponibilité des ouvrages réservés et la fermeture automatique des réservations non honorées au bout de 48h, avec notification du suivant en liste d'attente.

La fréquence de déclenchement du traitement est paramétrée dans le fichier **application.properties** au niveau du paramètre **batch.cron.value** elle est pour les besoins de tests paramétrée à 2 minutes 0 */2 * ? * *.  
Pour une fréquence quotidienne à 23 heure du lundi au vendredi il faut mettre  **&nbsp;0 0 23 * * 0-4&nbsp;**.

### Prérequis techniques  
Version de Java : 1.8  
JDK : jdk1.8.0_191  
Maven 3.6  

### Documentation
La javadoc peut être consultée en lançant le fichier **docs\index.html**  

##Installation et déploiement
Packaging : **mvn clean package**
Vous pouvez activer des tests automatisés sur la build en décommentant les directives @Test   
Pour fonctionner, les tests nécessitent que l'API soit en fonctionnement et que le serveur SMTP soit opérationnel (pour vos tests vous pouvez utiliser http://nilhcem.com/FakeSMTP/).  
Si par la suite vous ne voulez pas lancer les tests vous pouvez packager avec le paramètre  
**mvn clean package -DskipTests**  

Pour déclencher le traitement, aller dans target et lancer le jar avec la commande
**java -jar eLitBatch-0.0.1-SNAPSHOT.jar**

**Logs :** Par défaut le niveau de log est positionné à "INFO", les logs sont quotidiens (horodatés) et se trouvent dans le répertoire  **logs**, tous ces paramétrages sont dans le fichier **src\main\resources\logback.xml**

Sources disponibles sur : https://github.com/Rondoublec/eLit-V2  
La partie front est sous : https://github.com/Rondoublec/eLit-V2/tree/master/elitBatch

