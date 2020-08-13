package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.Reservation;
import fr.rbo.elitapi.entity.User;
import fr.rbo.elitapi.exceptions.ConflictException;
import fr.rbo.elitapi.exceptions.NotAcceptableException;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.EmpruntRepository;
import fr.rbo.elitapi.repository.OuvrageRepository;
import fr.rbo.elitapi.repository.ReservationRepository;
import fr.rbo.elitapi.repository.ReservationRepositoryInterface;
import fr.rbo.elitapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private static final String ERR_RESA_NOT_FOUND = "Reservation inexistante, non trouvée";

    @Value("${reservation.coef.nbmax.par.ouvrage}")
    private int coefNbMaxParOuvrage;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationRepositoryInterface reservationRecherche;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OuvrageRepository ouvrageRepository;
    @Autowired
    EmpruntRepository empruntRepository;


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
     * renvoie la liste des reservations répondants aux critères  action et/ou reservationCherche
     * @param action demandée
     * @param reservationCherche critères
     * @return liste des reservations correspondants au critères
     */
    @PostMapping(value="/reservations/recherche/{action}")
    public List<Reservation> listeDesReservationsSelonCriteres( @PathVariable("action") String action
            , @RequestBody Reservation reservationCherche){
        LOGGER.debug("Post /reservations/recherche/{action} " + action);
        List<Reservation> reservations = reservationRecherche.rechercheReservation(reservationCherche, action);
        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas d'ouvrage correspondant à votre recherche");
        return reservations;
    }

    /**
     * renvoie les informations de la réservation correspondant à l'id
     * @param id de la réservation
     * @return reservation
     */
    @GetMapping(value = "/reservation/{id}")
    public Optional<Reservation> recupererUneReservation (@PathVariable("id") Long id){
        LOGGER.debug("Get /reservation/{id} " + id);
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) throw new NotFoundException("Cette réservation n'existe pas");
        return reservation;
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
        List<Reservation> reservations =
                reservationRepository.findAllByOuvrageAndReservationActiveTrueOrderByReservationDateDemandeAsc(ouvrageRecherche);
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

        User user = userRepository.findByEmail(reservation.getUser().getEmail());
        if (user == null) {
            throw new NotFoundException("Utilisateur inconnu");
        }

        controlesMetier(ouvrage, user);

        reservation.setUser(user);
        reservation.setOuvrage(ouvrage);
        reservation.setNotifier(false);
        reservation.setReservationActive(true);
        reservation.setReservationDateDemande(Calendar.getInstance().getTime());
        reservationRepository.save(reservation);
        return reservation;
    }

    /**
     * met à jour sur la reservation de la date de notification
     * @param id de la reservation
     * @return reservation
     */
    @PutMapping(value = "/reservation/notification/{id}")
    public Reservation notififierDisponibiliteOuvrageReserve(@PathVariable("id") Long id){
        LOGGER.debug("Get /reservation/notification/{" + id + "}");
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ERR_RESA_NOT_FOUND));
        if (null != reservation.getReservationDateNotif()) {
            throw new NotAcceptableException("Demande éronnée, notification déjà effectuée");
        }
        reservation.setReservationDateNotif(Calendar.getInstance().getTime());
        reservation.setNotifier(false);
        reservationRepository.save(reservation);
        return reservation;
    }

    @PutMapping(value = "/reservation/anotifier/{id}")
    public Reservation reservationActiveaNotifier(@PathVariable("id") Long id){
        // Recherche si réservation non notifiées
        // SI c'est le cas ALORS notifier la réservation la plus ancienne (order by ReservationDateDemande asc)
        Ouvrage o = new Ouvrage();
        o.setOuvrageId(id);
        List<Reservation> reservations =
                reservationRepository.findAllByOuvrageAndReservationActiveTrueAndNotifierFalseAndReservationDateNotifIsNullOrderByReservationDateDemandeAsc(o);
        Reservation reservation = null;
        if (!reservations.isEmpty()) {
            // mise à jour du flag "à notifier" pour que le batch notifie l'utilisateur (mail + maj date de notification)
            reservation = switchNotifier(reservations.get(0).getReservationId());
        }
        return reservation;
    }

    /**
     * inverse le statut de la reservation : reservationActive true / false
     * @param id de la reservation
     * @return reservation
     */
    @PutMapping(value = "/reservation/inverseEtat/{id}")
    public Reservation switchEtatReservation (@PathVariable("id") Long id){
        LOGGER.debug("Put /reservation/inverseEtat/{" + id + "}");
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ERR_RESA_NOT_FOUND));
        if (Boolean.TRUE.equals(reservation.getReservationActive())) {
            reservation.setNotifier(false);
            reservation.setReservationActive(false);
        } else {
            reservation.setReservationActive(true);
        }
        reservationRepository.save(reservation);
        return reservation;
    }

    /**
     * inverse le flag "à notifier" de la reservation : notifier true / false
     * @param id de la reservation
     * @return reservation
     */
    @PutMapping(value = "/reservation/inverseNotifier/{id}")
    public Reservation switchNotifier (@PathVariable("id") Long id){
        LOGGER.debug("Put /reservation/inverseNotifier/{" + id + "}");
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ERR_RESA_NOT_FOUND));
        if (Boolean.TRUE.equals(reservation.getNotifier())) {
            reservation.setNotifier(false);
        } else {
            reservation.setNotifier(true);
        }
        reservationRepository.save(reservation);
        return reservation;
    }

    /**
     * renvoie la liste des reservations à notifier
     * @return liste des reservations
     */
    @GetMapping(value="/reservations/anotifier")
    public List<Reservation> listeDesReservationsAnotifier(){
        LOGGER.debug("Get /reservations/anotifier");
        List<Reservation> reservations =
                reservationRepository.findAllByNotifierTrueAndReservationActiveTrueAndReservationDateNotifIsNull();

        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas de reservation à notifier");
        return reservations;
    }

    /**
     * renvoie la liste des reservations activees notifiées
     * @return liste des reservations
     */
    @GetMapping(value="/reservations/notifiees")
    public List<Reservation> listeDesReservationsNotifiees(){
        LOGGER.debug("Get /reservations/notifiees");
        List<Reservation> reservations =
                reservationRepository.findAllByReservationActiveTrueAndReservationDateNotifIsNotNull();

        if (reservations.isEmpty()) throw new NotFoundException("Il n'y a pas de reservation active notifiee");
        return reservations;
    }

    private void controlesMetier (Ouvrage ouvrage, User user) {

        // RG001 : Si la liste des réservation en cours est > à 2 fois le stock, on ne peut pas ajouter une réservation supplémentaire
        List<Emprunt> emprunts = empruntRepository.findAllByOuvrageAndEmpruntRenduFalse(ouvrage);
        LOGGER.debug("/reservation/ajout -> emprunts en cours    :" + emprunts.size());
        int maxReservation = (Integer.parseInt(ouvrage.getOuvrageQuantite()) + emprunts.size()) * coefNbMaxParOuvrage;
        LOGGER.debug("/reservation/ajout -> maxReservation       : " + maxReservation);
        List<Reservation> reservations = reservationRepository.findAllByOuvrageAndReservationActiveTrue(ouvrage);
        int nbReservationEnAttente = reservations.size();
        LOGGER.debug("/reservation/ajout -> reservations actives : " + nbReservationEnAttente);
        if (nbReservationEnAttente >= maxReservation) {
            LOGGER.debug("RG001 : Réservation impossible, quotat de réservation en attente atteint ou rupture définitive ! " + nbReservationEnAttente);
            throw new NotAcceptableException("RG001 : Réservation impossible, quotat de réservation en attente atteint ou rupture définitive ! ");
        }

        // RG002 :  si l'usager a déjà cet emprunt en cours il ne peut le réserver sur le même ouvrage
        Emprunt emprunt = empruntRepository.findByUserAndOuvrageAndEmpruntRenduFalse(user, ouvrage);
        if (emprunt != null) {
            LOGGER.debug("RG002 : Réservation impossible, l'usager a un emprunt en cours sur cet ouvrage ! " + emprunt.getEmpruntId());
            throw new ConflictException("RG002 : Réservation impossible, l'usager a un emprunt en cours sur cet ouvrage ! ");
        }

        // RG003:  si l'usager a déjà une réservation active pour cet ouvrage il ne peut le réserver sur le même ouvrage
        Reservation reservation1 = reservationRepository.findByUserAndOuvrageAndReservationActiveTrue(user, ouvrage);
        if (reservation1 != null) {
            LOGGER.debug("RG003 : Réservation impossible, l'usager a une réservation active pour cet ouvrage ! " + reservation1.getReservationId());
            throw new ConflictException("RG003 : Réservation impossible, l'usager a une réservation active pour cet ouvrage ! ");
        }

    }

}
