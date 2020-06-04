package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.BibliothequeBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OuvragesControllerTests {

    private static final int CHOIX_BIBLIOTHEQUE = 700;

    @Autowired
    HttpServletRequest request;
    @Autowired
    OuvragesController ouvragesController;

    @Test
    void testRecupChoixBibliotheque(){
        request.getSession().setAttribute("bibliotheque", CHOIX_BIBLIOTHEQUE);
        BibliothequeBean bibliotheque = ouvragesController.choixBibliotheque();
        assertTrue(bibliotheque.getBibliothequeId() == (CHOIX_BIBLIOTHEQUE));

    }

}
