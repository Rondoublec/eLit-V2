package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.EmpruntBean;
import fr.rbo.elitweb.beans.OuvrageBean;
import fr.rbo.elitweb.beans.ReservationBean;
import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitweb.exceptions.ConflictException;
import fr.rbo.elitweb.exceptions.NotAcceptableException;
import fr.rbo.elitweb.exceptions.NotFoundException;
import fr.rbo.elitweb.proxies.APIProxy;
import fr.rbo.elitweb.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private static final String REDIRECT_MESRESERVATIONS = "redirect:/mesreservations";
    private static final String RESA = "resa";
    private static final String ANNUL = "annul";

    @Autowired
    APIProxy apiProxy;

    /**
     * Affiche la liste des réservations actives de l'utilisateur connecté
     * @param model model
     * @param httpSession Session
     * @param redirectAttributes attributs valorisés de la redirection
     * @return liste de réservations actives de l'utilisateur connecté
     */
    @GetMapping(path = "/mesreservations")
    public String mesReservations(Model model, HttpSession httpSession
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /mesreservations");
        ReservationBean reservationCriteres = new ReservationBean();
        reservationCriteres.setUser(recupUser());
        reservationCriteres.setReservationActive(true);
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.rechercheReservationCriteres(reservationCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("titre", "Mes réservations");
        model.addAttribute("reservationCriteres", reservationCriteres);
        model.addAttribute("reservations", reservations);
        return "recherche-reservations-list";
    }

    /**
     * Affiche la liste des réservations actives de l'utilisateur connecté
     * @param model model
     * @param reservationCriteres citères de recherche
     * @param httpSession Session
     * @return liste de reservations de l'utilisateur connecté correspondants aux critères de recherche
     */
    @PostMapping(path = "/mesreservations")
    public String reservationsRecherche(Model model, HttpSession httpSession,
                                        @ModelAttribute("reservationCriteres") ReservationBean reservationCriteres) {
        LOGGER.debug("Post /mesreservations");
        reservationCriteres.setUser(recupUser());
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.rechercheReservationCriteres(reservationCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("titre", "Résultats de la recherche");
        model.addAttribute("reservationCriteres", reservationCriteres);
        model.addAttribute("reservations", reservations);
        return "recherche-reservations-list";
    }

    @PostMapping(path = "/reservation/demande")
    public String reservationsDemande(Model model, HttpSession httpSession
            , @RequestParam("ouvrageId") long ouvrageId
            , final RedirectAttributes redirectAttributes){
        LOGGER.debug("Post /reservation/demande ouvrageId : " + ouvrageId);
        ReservationBean reservation = new ReservationBean();
        reservation.setUser(recupUser());
        OuvrageBean ouvrage = new OuvrageBean();
        ouvrage.setOuvrageId(ouvrageId);
        reservation.setOuvrage(ouvrage);

        try {
            apiProxy.creerReservation(reservation);
            redirectAttributes.addFlashAttribute(RESA, Constants.SUCCESS);
        } catch (NotAcceptableException e) {
            redirectAttributes.addFlashAttribute(RESA, "unsuccess001");
        } catch (ConflictException e) {
            redirectAttributes.addFlashAttribute(RESA, "unsuccess002");
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(RESA, "unsuccess404");
        }

        return REDIRECT_MESRESERVATIONS;
    }

    /**
     * Affiche les informations de la réservation, ainsi que
     * la liste des réservations actives pour cet ouvrage
     * et la liste des emprunt en cours avec leurs dates de retour prévues
     * @param reservationId de la réservation
     * @param model model
     * @param redirectAttributes attributs valorisés de la redirection
     * @return la réservation de l'utilisateur et les liste d'attente et date prévues de retrour
     */
    @GetMapping(path = "/reservation/details")
    public String details(@RequestParam("reservationId") int reservationId, Model model
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /reservation/details reservationId : " + reservationId);
        ReservationBean reservation = null;
        try {
            reservation = apiProxy.findReservationById(reservationId);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.NOT_FOUND);
            model.addAttribute(Constants.STATUS, Constants.NOT_FOUND);
            return REDIRECT_MESRESERVATIONS;
        }
        model.addAttribute("reservation", reservation);

        List<ReservationBean> reservationsOuvrage = null;
        reservationsOuvrage =
                apiProxy.listeDesReservationsDeLOuvrage((int) (long) reservation.getOuvrage().getOuvrageId());
        model.addAttribute("reservationsOuvrage", reservationsOuvrage);
        model.addAttribute("moi", recupUser().getEmail());

        EmpruntBean empruntCriteres = new EmpruntBean();
        empruntCriteres.setOuvrage(reservation.getOuvrage());
        empruntCriteres.setEmpruntRendu(false);
        List<EmpruntBean> empruntsOuvrage = null;
        try {
            empruntsOuvrage = apiProxy.rechercheEmpruntCriteres(empruntCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("empruntsOuvrage", empruntsOuvrage);

        return "details-reservation";
    }

    /**
     * Annule une réservation
     * @param reservationId de la réservation
     * @param model model
     * @param redirectAttributes attributs valorisés de la redirection
     * @return redirection vers la liste des réservations rafraichie
     */
    @GetMapping(path = "/reservation/inverseEtat")
    public String inverse(@RequestParam("reservationId") int reservationId, Model model
            , final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /reservation/inverseEtat reservationId : " + reservationId);
        ReservationBean reservation = null;
        try {
            reservation = apiProxy.findReservationById(reservationId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute(Constants.STATUS,Constants.NOT_FOUND);
            model.addAttribute(Constants.STATUS, Constants.NOT_FOUND);
            return REDIRECT_MESRESERVATIONS;
        }
        if (!reservation.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute(Constants.STATUS,Constants.NOT_AUTHORIZE);
            model.addAttribute(Constants.STATUS,Constants.NOT_AUTHORIZE);
            return REDIRECT_MESRESERVATIONS;
        }
        try {
            apiProxy.switchEtatReservation(reservationId);
            redirectAttributes.addFlashAttribute(ANNUL,Constants.SUCCESS);
            model.addAttribute(ANNUL, Constants.SUCCESS);
        } catch(NotAcceptableException e){
            redirectAttributes.addFlashAttribute(ANNUL,Constants.UNSUCCESS);
            model.addAttribute(ANNUL, Constants.UNSUCCESS);
        }
        return REDIRECT_MESRESERVATIONS;
    }

    private UserBean recupUser(){
        UserBean userCritere = new UserBean();
        userCritere.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        LOGGER.debug("recupUser critere email : " + userCritere.getEmail());
        return userCritere;
    }

}
