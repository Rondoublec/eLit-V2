package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Bibliotheque;
import fr.rbo.elitapi.entity.Ouvrage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OuvrageControllerTests {

    @Autowired
    private OuvrageController ouvrageController;

    @Test
    void recupererListeDesOuvrages() {
        // Arrange
        // Act
        List<Ouvrage> listeDesOuvrages = ouvrageController.listeDesOuvrages();
        // Assert
        Assertions.assertTrue(listeDesOuvrages.size() > 0);
    }

    @Test
    void recupererUnOuvrage(){
        // Arrange
        Long idRecherche = Long.valueOf("1000");
        // Act
        Ouvrage ouvrage = ouvrageController.recupererUnOuvrage(idRecherche);
        // Assert
        Assertions.assertTrue(idRecherche.equals(ouvrage.getOuvrageId()));
    }

    @Test
    void rechercherUnOuvrage(){
        // Arrange
        Bibliotheque bibliotheque = new Bibliotheque();
        bibliotheque.setBibliothequeId(Long.valueOf("700"));
        Ouvrage ouvrageRecherche = new Ouvrage();
        ouvrageRecherche.setBibliotheque(bibliotheque);
        ouvrageRecherche.setOuvrageTitre("story");
        ouvrageRecherche.setOuvrageAuteur("Auteur 1000");
        ouvrageRecherche.setOuvrageStyle("Comique");
        ouvrageRecherche.setOuvrageResume("histoire");
        ouvrageRecherche.setOuvrageQuantite("0");
        // Act
        List<Ouvrage> listeDesOuvrages = ouvrageController.listeDesOuvragesSelonCriteres(ouvrageRecherche);
        // Assert
        Assertions.assertTrue(listeDesOuvrages.size() > 0);
    }

}
