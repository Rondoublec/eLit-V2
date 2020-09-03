package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.EnCours;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmpruntControllerTests {

    @Autowired
    private EmpruntController empruntController;

    @Test
    void recupererListeDesEmprunts() {
        // Arrange
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmprunts();
        Assertions.assertTrue(listeDesEmprunts.size() > 0);
    }

    @Test
    void recupererUnEmprunt() {
        // Arrange
        Long idRecherche = Long.valueOf("6000");
        // Act
        Optional<Emprunt> emprunt = empruntController.recupererUnEmprunt(idRecherche);
        Assertions.assertTrue(emprunt.isPresent());
    }

    @Test
    void rechercherUnEmprunt(){
        // Arrange
        User user = new User();
        Ouvrage ouvrage = new Ouvrage();
        user.setEmail("a@a.a");
        ouvrage.setOuvrageTitre("story");
        ouvrage.setOuvrageReference("1000");
        Emprunt empruntRecherche = new Emprunt();
        empruntRecherche.setUser(user);
        empruntRecherche.setOuvrage(ouvrage);
        empruntRecherche.setEmpruntRelance(false);
        empruntRecherche.setEmpruntRendu(false);
        empruntRecherche.setEmpruntProlongation(false);
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmpruntsSelonCriteres("critere",empruntRecherche);
        // Assert
        Assertions.assertTrue(listeDesEmprunts.size() > 0);
    }
    @Test
    void rechercherListeDesEmpruntsRendus(){
        // Arrange
        Emprunt empruntRecherche = new Emprunt();
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmpruntsSelonCriteres("rendu",empruntRecherche);
        // Assert
        Assertions.assertTrue(listeDesEmprunts.get(0).getEmpruntRendu());
    }
    @Test
    void rechercherListeDesEmpruntsProlonges(){
        // Arrange
        Emprunt empruntRecherche = new Emprunt();
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmpruntsSelonCriteres("prolonge",empruntRecherche);
        // Assert
        Assertions.assertTrue(listeDesEmprunts.get(0).getEmpruntProlongation());
    }
    @Test
    void rechercherListeDesEmpruntsRelances(){
        // Arrange
        Emprunt empruntRecherche = new Emprunt();
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmpruntsSelonCriteres("relance",empruntRecherche);
        // Assert
        Assertions.assertTrue(listeDesEmprunts.get(0).getEmpruntRelance());
    }

    @Test
    void prolongerUnEmprunt(){
        // Arrange
        User user = new User();
        user.setId(Long.valueOf("400"));
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setOuvrageId(Long.valueOf("5601"));
        Emprunt emprunt = new Emprunt();
        emprunt.setUser(user);
        emprunt.setOuvrage(ouvrage);
        emprunt = empruntController.creerEmprunt(emprunt);
        // Act
        emprunt = empruntController.prolongerEmprunt(emprunt.getEmpruntId());
        // Assert
        Assertions.assertTrue(emprunt.getEmpruntProlongation());
    }
    @Test
    void supprimerUnEmprunt(){
        // Arrange
        User user = new User();
        user.setId(Long.valueOf("400"));
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setOuvrageId(Long.valueOf("5602"));
        Emprunt emprunt = new Emprunt();
        emprunt.setUser(user);
        emprunt.setOuvrage(ouvrage);
        emprunt = empruntController.creerEmprunt(emprunt);
        // Act
        String reponse = empruntController.supprimerEmprunt(emprunt.getEmpruntId());
        // Assert
        Assertions.assertTrue(reponse.equals("OK : Suppression réussie"));
    }
    @Test
    void retournerUnEmprunt(){
        // Arrange
        User user = new User();
        user.setId(Long.valueOf("400"));
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setOuvrageId(Long.valueOf("5603"));
        Emprunt emprunt = new Emprunt();
        emprunt.setUser(user);
        emprunt.setOuvrage(ouvrage);
        emprunt = empruntController.creerEmprunt(emprunt);
        // Act
        emprunt = empruntController.retournerEmprunt(emprunt.getEmpruntId());
        // Assert
        Assertions.assertTrue(emprunt.getEmpruntRendu());
    }

    @Test
    void recupererInfoEmpruntsEnCoursPourUnOuvrage(){
        // Arrange
        Long idRecherche = Long.valueOf("3000");
        // Act
        EnCours encours = empruntController.etatEmpruntEnCours(idRecherche);
        // Assert
        Assertions.assertTrue(encours.getNbEncours() > 0);
    }

    @Test
    void listeDesEmpruntsEnRetardParUser(){
        // Arrange
        Date dateDuJour = new Date();
        Long idUserRecherche = Long.valueOf("200");
        // Act
        List<Emprunt> listeDesEmprunts = empruntController.listeDesEmpruntsEnRetard(idUserRecherche);
        // Assert
        Assertions.assertTrue(dateDuJour.after(listeDesEmprunts.get(0).getEmpruntDateFin()));
    }

}
