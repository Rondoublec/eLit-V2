package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.Reservation;
import fr.rbo.elitapi.entity.User;
import fr.rbo.elitapi.exceptions.NotAcceptableException;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.OuvrageRepository;
import fr.rbo.elitapi.repository.ReservationRepository;
import fr.rbo.elitapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OuvrageRepository ouvrageRepository;


    /**
     * renvoie la liste des reservations
     * @return liste des reservations
     */
    @GetMapping(value="/reservations")
    public List<Reservation> listeDesReservations(){
        LOGGER.debug("Get /reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas de reservation en cours");
        return reservations;
    }

    /**
     * renvoie la liste des reservations en cours pour un ouvrage
     * @param id de l'ouvrage
     * @return liste des reservations
     */
    @GetMapping(value = "/reservations/ouvrage/{id}")
    public List<Reservation> listeDesReservationsDeLOuvrage(@PathVariable("id") Long id){
        LOGGER.debug("Get /reservations/ouvrage/{" + id + "}");
        Ouvrage ouvrageRecherche = ouvrageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Ouvrage inexistant"));
        List<Reservation> reservations = reservationRepository.findAllByOuvrageAndReservationActiveTrue(ouvrageRecherche);
        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas de reservation en cours pour cet ouvrage");
        return reservations;
    }

    /**
     * renvoie la liste des reservations en cours pour un utilisateur
     * @param id de l'utilisateur
     * @return liste des reservations
     */
    @GetMapping(value = "/reservations/user/{id}")
    public List<Reservation> listeDesReservationsDeCeUser(@PathVariable("id") Long id){
        LOGGER.debug("Get /reservations/user/{" + id + "}");
        User userRecherche = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Utilisateur inexistant"));
        List<Reservation> reservations = reservationRepository.findAllByUserAndReservationActiveTrue(userRecherche);
        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas de reservation en cours pour cet utilisateur");
        return reservations;
    }

    /**
     * crée une reservation pour l'utilisateur et l'ouvrage spécifiés dans le flux
     * @param reservation informations de la reservation
     * @return reservation
     */
    @PostMapping(value = "/reservation/ajout")
    public Reservation creerReservation (@RequestBody Reservation reservation){
        LOGGER.debug("Post /reservation/ajout");
        Ouvrage ouvrage = ouvrageRepository.findById(reservation.getOuvrage().getOuvrageId()).orElseThrow(() ->
                new NotFoundException("Ouvrage inconnu"));
        // TODO règles de gestion sur les quantités
        // TODO règles de gestion sur les états
//        if (RG001) {
//            throw new NotAcceptableException("Réservation impossible, ...");
//        }
        User user = userRepository.findById(reservation.getUser().getId()).orElseThrow(() ->
                new NotFoundException("Utilisateur inconnu"));
        // TODO règles de gestion sur les emprunts et reservations de l'utilisateur

        reservation.setUser(user);
        reservation.setOuvrage(ouvrage);
        reservation.setReservationActive(true);
        reservation.setReservationDateDemande(Calendar.getInstance().getTime());
        reservationRepository.save(reservation);
        return reservation;
    }

    /**
     * met à jour sur la reservation de la date de notification
     * @param id de la reservation
     * @return emprunt
     */
    @GetMapping(value = "/reservation/notification/{id}")
    public Reservation notififierDisponibiliteOuvrageReserve (@PathVariable("id") Long id){
        LOGGER.debug("Get /reservation/notification/{" + id + "}");
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Reservation inexistante, non trouvée"));
        if (null != reservation.getReservationDateNotif()) {
            throw new NotAcceptableException("Demande éronnée, notification déjà effectuée");
        }
        reservation.setReservationDateNotif(Calendar.getInstance().getTime());
        reservationRepository.save(reservation);
        return reservation;
    }

    /**
     * inverse le statut de reservation : reservationActive true / false
     * @param id de la reservation
     * @return emprunt
     */
    @GetMapping(value = "/reservation/inverseEtat/{id}")
    public Reservation switchEtatReservation (@PathVariable("id") Long id){
        LOGGER.debug("Get /reservation/inverseEtat/{" + id + "}");
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Reservation inexistante, non trouvée"));
        if (reservation.getReservationActive()) {
            reservation.setReservationActive(false);
        } else {
            reservation.setReservationActive(true);
        }
        reservationRepository.save(reservation);
        return reservation;
    }

}
