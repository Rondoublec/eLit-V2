-- le mot de passe de chaque user est 12345
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (100,TRUE,'alain@a.a','Admin','Alain','$2a$10$XAOiM6rLRrzMfF4jNsQc7O8jurI4BORxgh6cEGV0k9gymUfjYZx9a');
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (200,TRUE,'ursula@a.a','User','Ursula','$2a$10$2rCrmX.jiEAF1cLdDJduBOAj2qiim6bu9mKF86I3YvrHwNJqvijYW');
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (300,TRUE,'maurice@a.a','Membre','Maurice','$2a$10$jUmvMaNIcH6xE3M8TRp6suTYNmBR08/SBXQ.7iiyTZCa3IWG1o0q6');
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (400,TRUE,'u2@a.a','User2','Ulrich','$2a$10$r5LYG4bcXnFQfXSUFXWLIu8rzSqdOfHPD/SZ9/jJj6aZSXjpAtAsu');
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (500,TRUE,'u3@a.a','User3','Urbain','$2a$10$KicK13YZIjKkmptOwXACg.85TD6d8zcfxN2WcgEWbku5asdgfdvcu');
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (600,TRUE,'m1@a.a','Membre1','Manu','$2a$10$6HdfSXRm2fhvLc6BJQEmPunFIc6hWY94X9/DLQUaHNvc5AAyq/vLO');
-- INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (601,TRUE,'a@a.a','User','a','$2a$10$/H.hSoh2tn5PZ5Kdcsf1P.gnha7q3u.TQv4ghs/XnxNnd26Z5tuJm'); Mot de passe vide
INSERT INTO UTILISATEUR (ID,ACTIVE,EMAIL,LAST_NAME,NAME,PASSWORD) VALUES (601,TRUE,'a@a.a','Le Petit','Robert','$2a$10$q0emfCrFlqeFfbsNWGlLaeHdqwCGBUV9CzBm3qdvXyZdCPDFFkdn.');


INSERT INTO ROLE (ROLE_ID,ROLE) VALUES (101,'ADMIN');
INSERT INTO ROLE (ROLE_ID,ROLE) VALUES (201,'USER');
INSERT INTO ROLE (ROLE_ID,ROLE) VALUES (301,'PERSONNEL');

INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (100,101);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (200,201);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (300,301);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (400,201);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (500,201);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (600,301);
INSERT INTO UTILISATEUR_ROLES (USER_ID,ROLES_ROLE_ID) VALUES (601,201);

INSERT INTO BIBLIOTHEQUE (BIBLIOTHEQUE_ID, BIBLIOTHEQUE_NOM, BIBLIOTHEQUE_ADRESSE, BIBLIOTHEQUE_EMAIL, BIBLIOTHEQUE_HORAIRES, BIBLIOTHEQUE_TELEPHONE)
VALUES (700,'La corderie','15 rue des lombards 30000 Nimes','lc@nm.fr','Lun.Ven. 10-12H 14-18H; Sam. 9-17H','04.66.84.02.83');
INSERT INTO BIBLIOTHEQUE (BIBLIOTHEQUE_ID, BIBLIOTHEQUE_NOM, BIBLIOTHEQUE_ADRESSE, BIBLIOTHEQUE_EMAIL, BIBLIOTHEQUE_HORAIRES, BIBLIOTHEQUE_TELEPHONE)
VALUES (701,'Nimes ouest','1 place de la revolution 30000 Nimes','no@nm.fr','Mar.Ven. 10-12H 14-18H; Sam. 10-12 13-16H','04.66.84.00.78');
INSERT INTO BIBLIOTHEQUE (BIBLIOTHEQUE_ID, BIBLIOTHEQUE_NOM, BIBLIOTHEQUE_ADRESSE, BIBLIOTHEQUE_EMAIL, BIBLIOTHEQUE_HORAIRES, BIBLIOTHEQUE_TELEPHONE)
VALUES (702,'Nimes sud','8 rue Paul Painlevé 30000 Nimes','ns@nm.fr','Mar.Ven. 10-12H 14-18H; Mer. 13-19H, Sam. 9-17H','04.66.67.69.83');

INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (1000,'Auteur 1000','Etage 1000 Casier 1000',0,'REF1000','C''est l''histoire d''un mec ...','Comique','The story of a guy','../images/guy.jpg',700);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (2000,'Auteur 2000','Etage 2000 Casier 2000',2,'REF2000','C''est l''histoire de 2 mecs ...','Amour','The story of a guy 2','../images/guy2.jpg',700);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (3000,'Auteur 3000','Etage 3000 Casier 3000',0,'REF3000','C''est l''histoire de 3 mecs ...','Thriller','The story of a guy 3','../images/guy3.jpg',700);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (4000,'Auteur 4000','Etage 4000 Casier 4000',4,'REF4000','C''est l''histoire de 4 mecs ...','Amour','The story of a guy 4','../images/guy4.jpg',700);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (5000,'Auteur 5000','Etage 5000 Casier 5000',0,'REF5000','C''est l''histoire de 5 mecs ...','Science-fiction','The story of a guy 5','../images/guy5.jpg',700);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (5500,'Auteur 5500','Etage 5500 Casier 5500',0,'REF5500','C''est l''histoire de 6 mecs ...','Fantastique','The story of a guy 6','../images/guy6.jpg',701);
INSERT INTO OUVRAGE (OUVRAGE_ID,OUVRAGE_AUTEUR,OUVRAGE_LOCALISATION,OUVRAGE_QUANTITE,OUVRAGE_REFERENCE,OUVRAGE_RESUME,OUVRAGE_STYLE,OUVRAGE_TITRE,OUVRAGE_LIEN_IMAGE, BIBLIOTHEQUE_BIBLIOTHEQUE_ID)
VALUES (5600,'Auteur 5600','Etage 5600 Casier 5600',0,'REF5600','C''est l''histoire de 7 mecs ...','BD','The story of a guy 7','../images/guy7.jpg',702);

INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9200,'2020-08-01 14:00:00.000','2020-08-28 14:00:00.000',NULL,NULL,NULL,3000,100,FALSE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9300,'2020-07-20 14:00:00.000','2020-08-18 14:00:00.000','2020-07-14 14:00:00.000',NULL,NULL,3000,600,TRUE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9400,'2020-07-30 14:00:00.000','2020-08-25 14:00:00.000',NULL,NULL,NULL,3000,300,FALSE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (6000,'2020-07-02 14:00:00.000','2020-08-01 14:00:00.000',NULL,NULL,NULL,2000,200,FALSE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (7000,'2020-07-02 14:00:00.000','2020-08-01 14:00:00.000',NULL,'2020-07-29 14:00:00.000',NULL,3000,601,FALSE,TRUE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (8000,'2020-07-02 14:00:00.000','2020-08-01 14:00:00.000','2020-08-29 14:00:00.000',NULL,NULL,4000,601,TRUE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9000,'2020-07-02 14:00:00.000','2020-08-01 14:00:00.000',NULL,NULL,NULL,5000,200,FALSE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9100,'2020-07-05 14:00:00.000','2020-07-31 14:00:00.000',NULL,NULL,NULL,1000,601,FALSE,FALSE,FALSE);
INSERT INTO EMPRUNT (EMPRUNT_ID,EMPRUNT_DATE_DEBUT,EMPRUNT_DATE_FIN,EMPRUNT_DATE_PROLONGATION,EMPRUNT_DATE_RETOUR,EMPRUNT_DATE_RELANCE,OUVRAGE_OUVRAGE_ID,USER_ID,EMPRUNT_PROLONGATION, EMPRUNT_RENDU, EMPRUNT_RELANCE )
VALUES (9150,'2020-07-09 14:00:00.000','2020-08-06 14:00:00.000',NULL,NULL,NULL,5500,200,FALSE,FALSE,FALSE);

INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9501,FALSE,FALSE,'2020-08-01 12:00:00.000',NULL,4000,601);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9502,FALSE,FALSE,'2020-08-02 12:00:00.000',NULL,5000,601);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9503,FALSE,TRUE,'2020-07-13 12:00:00.000',NULL,1000,200);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9510,FALSE,TRUE,'2020-07-26 12:00:00.000',NULL,3000,200);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9520,FALSE,TRUE,'2020-07-30 12:00:00.000',NULL,3000,400);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9530,FALSE,TRUE,'2020-07-22 12:00:00.000',NULL,3000,500);
-- INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
-- VALUES  (9540,FALSE,TRUE,'2020-07-25 12:00:00.000',NULL,5500,601);
INSERT INTO RESERVATION (RESERVATION_ID,NOTIFIER,RESERVATION_ACTIVE,RESERVATION_DATE_DEMANDE,RESERVATION_DATE_NOTIF,OUVRAGE_OUVRAGE_ID,USER_ID)
VALUES  (9560,FALSE,TRUE,'2020-07-29 12:00:00.000',NULL,3000,601);
