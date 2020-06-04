# eLitAPI : Base de données PosgreSQL avec jeux de données de tests 

## Dumps & restauration avec JDD
Les Dumps founis ont été réalisés avec la fonction backup de pgAdmin 4
elit_custo (backup mode custom)
elit_plain (backup mode plain -> requêtes en claires si vous préférer garder la maitrise et les lancer manuellement).
Les contenus sont sont identiques, choisissez en fonction de votre mosde de restauration.

Avant toute choses depuis pgAdmin 4
- créer un compte **adm_elit** avec privilèges (il faut qu'il est celui de login aussi)
- créer une base de données **bdd_elit** dont le owner est **adm_elit**
- Ensuite sur la base bdd_elit (clique droit) faite le choix [retore]
- Choisir le mode [Custom] et aller selectionner le fichier elit_custo (dans \eLit-V2\elitAPI\bdd_elit).
- Cliquer sur Restore.

Voila vos tables sont créées et peupléées avec un jeu de données d'exemples.

