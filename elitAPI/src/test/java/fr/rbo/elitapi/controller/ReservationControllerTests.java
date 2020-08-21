package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.Reservation;
import fr.rbo.elitapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ReservationControllerTests {

    @Autowired
    private ReservationController reservationController;

    @Test
    void recupererListeDesReservations() {
        // Arrange
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservations();
        // Assert
        Assertions.assertTrue(listeDesReservations.size() > 0);
    }
    @Test
    void recupererUneReservation() {
        // Arrange
        Long idRecherche = Long.valueOf("9501");
        // Act
        Optional<Reservation> reservation = reservationController.recupererUneReservation(idRecherche);
        // Assert
        Assertions.assertTrue(reservation.isPresent());
    }

    @Test
    void rechercherUneReservation(){
        // Arrange
        User user = new User();
        Ouvrage ouvrage = new Ouvrage();
        user.setEmail("a@a.a");
        ouvrage.setOuvrageTitre("story");
        ouvrage.setOuvrageReference("3000");
        Reservation reservationRecherche = new Reservation();
        reservationRecherche.setUser(user);
        reservationRecherche.setOuvrage(ouvrage);
        reservationRecherche.setReservationActive(true);
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsSelonCriteres("critere",reservationRecherche);
        // Assert
        Assertions.assertTrue(listeDesReservations.size() > 0);
    }
    @Test
    void rechercherListeDesReservationsActives(){
        // Arrange
        Reservation reservationRecherche = new Reservation();
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsSelonCriteres("active",reservationRecherche);
        // Assert
        Assertions.assertTrue(listeDesReservations.get(0).getReservationActive());
    }
    @Test
    void rechercherListeDesReservationsCloses(){
        // Arrange
        Reservation reservationRecherche = new Reservation();
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsSelonCriteres("close",reservationRecherche);
        // Assert
        Assertions.assertFalse(listeDesReservations.get(0).getReservationActive());
    }
    @Test
    void rechercherListeDesReservationsNotifiees(){
        // Arrange
        Reservation reservationRecherche = new Reservation();
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsSelonCriteres("notif",reservationRecherche);
        // Assert
        Assertions.assertTrue(listeDesReservations.get(0).getReservationDateNotif() != null);
    }

    @Test
    void rechercherLesReservationDeLOuvrage(){
        // Arrange
        Long idOuvrageRecherche = Long.valueOf("3000");
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsDeLOuvrage(idOuvrageRecherche);
        // Assert
        Assertions.assertTrue(listeDesReservations.size() > 0);
    }
    @Test
    void rechercherLesReservationDuUser(){
        // Arrange
        Long idUserRecherche = Long.valueOf("601");
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsDeCeUser(idUserRecherche);
        // Assert
        Assertions.assertTrue(listeDesReservations.size() > 0);
    }

    @Test
    void creerUneReservation(){
        // Arrange
        User user = new User();
        user.setEmail("a@a.a");
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setOuvrageId(Long.valueOf("5000"));
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setOuvrage(ouvrage);
        // Act
        reservation = reservationController.creerReservation(reservation);
        // Assert
        Assertions.assertTrue(reservation.getReservationActive());
    }

    @Test
    void majDateDeNotificationDeLaReservation(){
        // Arrange
        Long idRecherche = Long.valueOf("9503");
        // Act
        Reservation reservation = reservationController.notififierDisponibiliteOuvrageReserve(idRecherche);
        // Assert
        Assertions.assertTrue(reservation.getReservationDateNotif() != null);
    }
    @Test
    void reservationActiveaNotifier(){
        // Arrange
        Long idRecherche = Long.valueOf("3000");
        // Act
        Reservation reservation = reservationController.reservationActiveaNotifier(idRecherche);
        // Assert
        Assertions.assertTrue(reservation.getNotifier());
    }

    @Test
    void switchEtatReservation(){
        // Arrange
        Long idRecherche = Long.valueOf("9501");
        // Act
        Reservation reservation = reservationController.switchEtatReservation(idRecherche);
        // Assert
        Assertions.assertTrue(reservation.getReservationActive());
    }
    @Test
    void switchNotifier(){
        // Arrange
        Long idRecherche = Long.valueOf("9502");
        // Act
        Reservation reservation = reservationController.switchNotifier(idRecherche);
        // Assert
        Assertions.assertTrue(reservation.getNotifier());
    }

    @Test
    void rechercherListeDesReservationsActivesAnotifier(){
        // Arrange
        Long idRecherche = Long.valueOf("9520");
        Reservation reservation = reservationController.switchNotifier(idRecherche);
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsAnotifier();
        // Assert
        Assertions.assertTrue(listeDesReservations.get(0).getReservationActive());
    }

    @Test
    void rechercherListeDesReservationsActivesNotifiees(){
        // Arrange
        // Act
        List<Reservation> listeDesReservations = reservationController.listeDesReservationsNotifiees();
        // Assert
        Assertions.assertTrue(listeDesReservations.get(0).getReservationDateNotif() != null);
    }

    //TODO RG métier à tester
}
