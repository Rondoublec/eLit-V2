## eLit-V1

### Contexte  
Il s'agit du 7eme projet du cursus Développeur d'application Java proposé par OpenClassrooms **Développez le nouveau système d’information de la bibliothèque d’une grande ville (Front / API / Batch)** .  
Développé par Rémy Bourdoncle. 


###Release 1.0
Cette release 1.0 propose une site web à l'attention des adhérents des bibliothèques municipales d'une ville pour rechercher des ouvrages et gérer leurs emprunts. Elles se compose de 3 modules :
 
**eLitAPI** : il s'agit de l'API qui permet de gérer les objets et activités autour des ouvrages et emprunts.
Elle permet de gérer : 
- les comptes utilisateurs et leurs droits (création, consultation)
- Les ouvrages (création, consultation, modification)
- Les emprunts (création, consultation, modification, restitution, prolongation, suppression, sélection des retards)
-> en complément, les actions de création d'emprunt et restitution d'emprunt mettent à jour la quantité disponible d'un ouvrage 
- Les bibliothèques (consultation)
Toutes ces fonctionnalités, ne sont pas nécessaires pour la Release 1.0 mais le seront pour les prochaines release (client mobile, application de gestion à l'attention du personnel des bibliothèques, évolution futures).

**elitClient** : il s'agit de l'application WEB mise à disposition des adhérents, elle permet à un adhérent : 
- de se connecter à son compte (de le créer si nécessaire)
- de choisir une bibliothèque préférée et d'en consulter les informations
- de rechercher et consulter les informations sur les ouvrages
- de consulter ses emprunts
- de prolonger ses emprunts

**elitBatch** : il s'agit d'un traitement automatisé de relance des retards de restitution d'ouvrages empruntés, il permet : 
- Une analyse, automatique ordonnancée à une fréquence déterminée, des retards
- De récupérer la liste de retards par adhérent
- D'envoyer un mail à l'adhérent lui rappelant la liste des ouvrages dont la date de restitution est dépassée

Ces 3 applications sont indépendantes et packagées par Maven.
Un README.md accompagne chaque module et documente le fonctionnement et le packaging.

Le site Web utilise l'API pour fonctionner (le site WEB peut être lancé sans API, mais aucune fonctionnalité ne sera disponible).

Le batch utilise l'API pour la récupération des informations et un serveur SMTP pour envoyer les eMails (dans le cadre du développement on peut utiliser une application gratuite comme fakeSMTP pour tester localement l'envoi de mails)

L'API est sécurisée par une BasicAuth et l'application WEB est sécurisée par utilisateurs.


