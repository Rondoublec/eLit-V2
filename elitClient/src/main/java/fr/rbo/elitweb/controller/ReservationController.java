package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.EmpruntBean;
import fr.rbo.elitweb.beans.EnCoursBean;
import fr.rbo.elitweb.beans.OuvrageBean;
import fr.rbo.elitweb.beans.ReservationBean;
import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitweb.exceptions.NotAcceptableException;
import fr.rbo.elitweb.exceptions.NotFoundException;
import fr.rbo.elitweb.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    APIProxy apiProxy;

    /**
     * Affiche la liste des réservations actives de l'utilisateur connecté
     * @param model model
     * @param httpSession Session
     * @param redirectAttributes attributs valorisés de la redirection
     * @return liste de réservations actives de l'utilisateur connecté
     */
    @RequestMapping(value="/mesreservations", method = RequestMethod.GET)
    public String MesReservations(Model model, HttpSession httpSession
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /mesreservations");
        ReservationBean reservationCriteres = new ReservationBean();
        reservationCriteres.setUser(recupUser());
        reservationCriteres.setReservationActive(true);
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.rechercheReservationCriteres(reservationCriteres);
        } catch(NotFoundException e){}
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
    @RequestMapping(value="/mesreservations", method = RequestMethod.POST)
    public String ReservationsRecherche(Model model, HttpSession httpSession,
                                        @ModelAttribute("reservationCriteres") ReservationBean reservationCriteres) {
        LOGGER.debug("Post /mesreservations");
        reservationCriteres.setUser(recupUser());
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.rechercheReservationCriteres(reservationCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("reservationCriteres", reservationCriteres);
        model.addAttribute("reservations", reservations);
        return "recherche-reservations-list";
    }

    @RequestMapping(value="/reservation/demande", method = RequestMethod.POST)
    public String ReservationsDemande(Model model, HttpSession httpSession
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
            redirectAttributes.addFlashAttribute("resa","success");
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("resa","unsuccess");
        }

        return "redirect:/mesreservations";
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
    @RequestMapping(value = "/reservation/details", method = RequestMethod.GET)
    public String details(@RequestParam("reservationId") int reservationId, Model model
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /reservation/details reservationId : " + reservationId);
        ReservationBean reservation = null;
        try {
            reservation = apiProxy.findReservationById(reservationId);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("status", "notFound");
            model.addAttribute("status", "notFound");
            return "redirect:/mesreservations";
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
    @RequestMapping(value = "/reservation/inverseEtat", method = RequestMethod.GET)
    public String inverse(@RequestParam("reservationId") int reservationId, Model model
            , final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /reservation/inverseEtat reservationId : " + reservationId);
        ReservationBean reservation = null;
        try {
            reservation = apiProxy.findReservationById(reservationId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("status","notFound");
            model.addAttribute("status", "notFound");
            return "redirect:/mesreservations";
        }
        if (!reservation.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            model.addAttribute("status","notAuthorize");
            return "redirect:/mesreservations";
        }
        try {
            reservation = apiProxy.switchEtatReservation(reservationId);
            redirectAttributes.addFlashAttribute("annul","success");
            model.addAttribute("annul", "success");
        } catch(NotAcceptableException e){
            redirectAttributes.addFlashAttribute("annul","unsuccess");
            model.addAttribute("annul", "unsuccess");
        }
        return "redirect:/mesreservations";
    }

    private UserBean recupUser(){
        UserBean userCritere = new UserBean();
        userCritere.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        LOGGER.debug("recupUser critere email : " + userCritere.getEmail());
        return userCritere;
    }

}
