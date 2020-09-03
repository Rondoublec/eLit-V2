package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Test
    void recupererListeDesUsers() {
        // Arrange
        // Act
        List<User> listeDesUsers = userController.listeDesUsers();
        //Assert
        Assertions.assertTrue(listeDesUsers.size() > 0);
    }
    @Test
    void recupererUnUser() {
        // Arrange
        Long idRecherche = Long.valueOf("601");
        // Act
        User user = userController.recupererUser(idRecherche);
        //Assert
        Assertions.assertTrue(idRecherche.equals(user.getId()));
    }
    @Test
    void recupererUnUserParEmail() {
        // Arrange
        String emailRecherche = "a@a.a";
        // Act
        User user = userController.recupererUserParEmail(emailRecherche);
        //Assert
        Assertions.assertTrue(emailRecherche.equals(user.getEmail()));
    }
}
