package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Bibliotheque;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BibliothequeControllerTests {

    @Autowired
    private BibliothequeController bibliothequeController;

    @Test
    void recupererListeDeBibliotheques() {
        // Arrange
        // Act
        List<Bibliotheque> listeDesBibliotheques = bibliothequeController.listeDesBibliotheques();
        Assertions.assertTrue(listeDesBibliotheques.size() > 0);
    }
}
