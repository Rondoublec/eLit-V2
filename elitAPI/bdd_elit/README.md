# eLitAPI : Base de données PosgreSQL avec jeux de données de tests 

## Dumps & restauration avec JDD
Les Dumps founis ont été réalisés avec la fonction backup de pgAdmin 4
elitv2_custo (backup mode custom)
elitv2_plain (backup mode plain -> requêtes en claires si vous préférer garder la maitrise et les lancer manuellement).
Les contenus sont identiques, choisissez en fonction de votre mode de restauration.

Avant toute choses depuis pgAdmin 4
- créer un compte **usr_elit** avec privilèges (il faut qu'il ait celui de login aussi)
- créer une base de données **bdd_elitv2** dont le owner est **usr_elit**
- Ensuite sur la base bdd_elitv2 (clique droit) faite le choix [restore]
- Choisir le mode [Custom] et aller selectionner le fichier elit_custo (dans \eLit-V2\elitAPI\bdd_elit\elitv2_custo).
- Cliquer sur Restore.

Voila vos tables sont créées et peuplées avec un jeu de données d'exemples.

