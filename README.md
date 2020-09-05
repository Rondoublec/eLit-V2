## eLit-V2

### Contexte  
Il s'agit du 10eme projet du cursus Développeur d'application Java proposé par OpenClassrooms **Améliorez le système d’information de la bibliothèque d’une grande ville (Front / API / Batch)** .  
Développé par Rémy Bourdoncle. 


### Release 2.0
Cette release 2.0 Ajoute la gestion des réservations d'ouvrages aux fonctionnalités présentes dans la release 1.0 qui propose une site web à l'attention des adhérents des bibliothèques municipales d'une ville pour rechercher des ouvrages et gérer leurs emprunts. Elles se compose de 3 modules :
 
**eLitAPI** : il s'agit de l'API qui permet de gérer les objets et activités autour des ouvrages et emprunts.
Elle permet de gérer : 
- les comptes utilisateurs et leurs droits (création, consultation)
- Les ouvrages (création, consultation, modification)
- Les emprunts (création, consultation, modification, restitution, prolongation, suppression, sélection des retards)
-> en complément, les actions de création d'emprunt et restitution d'emprunt mettent à jour la quantité disponible d'un ouvrage 
- Les bibliothèques (consultation)
- **NEW V2.0** Les réservations d'ouvrages (création, consultation, gestion)
- **NEW V2.0** Blocage des demandes de prolongations d'emprunts pour les emprunts dont la date de restitution de l'ouvrage est dépassée.

Certaines fonctionnalités exposées par l'API ne sont pas utilisée, mais ont été développées en prévision des futurs évolutions (client mobile, application de gestion à l'attention du personnel des bibliothèques, autres fonctionnalités).

**elitClient** : il s'agit de l'application WEB mise à disposition des adhérents, elle permet à un adhérent : 
- de se connecter à son compte (de le créer si nécessaire)
- de choisir une bibliothèque préférée et d'en consulter les informations
- de rechercher et consulter les informations sur les ouvrages
- de consulter ses emprunts
- de prolonger ses emprunts
- **NEW V2.0** Effectuer une réservation, consulter et gérer ses réservations
- **NEW V2.0** Inactivation de la possibilité de demander la prolongations d'un emprunt si la date de restitution de l'ouvrage est dépassée.

**elitBatch** : il s'agit d'un traitement automatisé de relance des retards de restitution d'ouvrages empruntés et de notification de disponibilité d'une réservation, il permet : 
- Une analyse, automatique ordonnancée à une fréquence déterminée, des retards
- De récupérer la liste de retards par adhérent
- D'envoyer un mail à l'adhérent lui rappelant la liste des ouvrages dont la date de restitution est dépassée
- **NEW V2.0** envoyer un message aux adhérents ayant réservé un ouvrage lorsque celui-ci est disponible et annuler les réservations non honorées

Ces 3 applications sont indépendantes et packagées par Maven.
Un README.md accompagne chaque module et documente le fonctionnement et le packaging.

Le site Web utilise l'API pour fonctionner (le site WEB peut être lancé sans API, mais aucune fonctionnalité ne sera disponible).

Le batch utilise l'API pour la récupération des informations et un serveur SMTP pour envoyer les eMails (dans le cadre du développement on peut utiliser une application gratuite comme fakeSMTP pour tester localement l'envoi de mails)

L'API est sécurisée par une BasicAuth et l'application WEB est sécurisée par utilisateurs.


