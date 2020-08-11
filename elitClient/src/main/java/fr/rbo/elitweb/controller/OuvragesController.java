package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.BibliothequeBean;
import fr.rbo.elitweb.beans.EnCoursBean;
import fr.rbo.elitweb.beans.OuvrageBean;
import fr.rbo.elitweb.beans.ReservationBean;
import fr.rbo.elitweb.exceptions.NotFoundException;
import fr.rbo.elitweb.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OuvragesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OuvragesController.class);

    private static final String REDIRECT_OUVRAGES = "redirect:/ouvrages";
    private static final String REDIRECT_BIBLIOTHEQUES = "redirect:/bibliotheques";

    @Autowired
    APIProxy apiProxy;
    @Autowired
    HttpServletRequest request;

    @GetMapping(path = "/ouvrages")
    public String ouvrages(Model model, HttpSession httpSession
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /ouvrages");
        OuvrageBean ouvrageCriteres = new OuvrageBean();
        ouvrageCriteres.setBibliotheque(choixBibliotheque());
        try {
            if (ouvrageCriteres.getBibliotheque().getBibliothequeId().toString().isEmpty()) { return REDIRECT_BIBLIOTHEQUES; }}
        catch (Exception e) { return REDIRECT_BIBLIOTHEQUES; }
        List<OuvrageBean> ouvrages = null;
        try {
            ouvrages = apiProxy.rechercheOuvrage(ouvrageCriteres); }
        catch (NotFoundException e) {}
        model.addAttribute("titre", "Les plus populaires !");
        model.addAttribute("ouvrageCriteres", ouvrageCriteres);
        model.addAttribute("ouvrages", ouvrages);
        return "recherche-ouvrages-list";
    }

    @PostMapping(path = "/ouvrages")
    public String ouvragesRecherche(Model model,
                                    @ModelAttribute("ouvrageCriteres") OuvrageBean ouvrageCriteres,
                                    HttpSession httpSession) {
        LOGGER.debug("Post /ouvrages");
        ouvrageCriteres.setBibliotheque(choixBibliotheque());
        try {
            if (ouvrageCriteres.getBibliotheque().getBibliothequeId().toString().isEmpty()) { return REDIRECT_BIBLIOTHEQUES; }}
        catch (Exception e) { return REDIRECT_BIBLIOTHEQUES; }
        List<OuvrageBean> ouvrages = null;
        try {
            ouvrages = apiProxy.rechercheOuvrage(ouvrageCriteres); }
        catch (NotFoundException e) {}
        model.addAttribute("titre", "Résultats de la recherche");
        model.addAttribute("ouvrageCriteres", ouvrageCriteres);
        model.addAttribute("ouvrages", ouvrages);
        return "recherche-ouvrages-list";
    }

    @GetMapping(path = "/ouvrage/details")
    public String details(@RequestParam("ouvrageId") int ouvrageId, Model model
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /ouvrages/details ouvrageId : " + ouvrageId);
        OuvrageBean ouvrage = null;
        try {
            ouvrage = apiProxy.findOuvrageById(ouvrageId);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("status", "notFound");
            model.addAttribute("status", "notFound");
            return REDIRECT_OUVRAGES;
        }
        model.addAttribute("ouvrage", ouvrage);

        if (Integer.parseInt(ouvrage.getOuvrageQuantite()) == 0){
            model.addAttribute("isdisponible", false);
            EnCoursBean enCours = recupInfosEnCours(ouvrageId);
            model.addAttribute("encours", enCours);
            int nbResa = recupNbResa(ouvrageId);
            model.addAttribute("nbresa", nbResa);
            model.addAttribute("isreservable", isReservable(enCours, nbResa));
        } else {
            model.addAttribute("isdisponible", true);
            model.addAttribute("isreservable", false);
            model.addAttribute("nbresa", 0);
        }

        return "details-ouvrage";
    }

    protected EnCoursBean recupInfosEnCours (int ouvrageId) {
        EnCoursBean enCours = null;
        try {
            enCours = apiProxy.etatEmpruntEnCours(ouvrageId);
        } catch (NotFoundException e) {}
        return enCours;
    }

    protected Integer recupNbResa (int ouvrageId) {
        int nbResa = 0;
        List<ReservationBean> reservationBean;
        try {
            reservationBean = apiProxy.listeDesReservationsDeLOuvrage(ouvrageId);
            nbResa = reservationBean.size();
        } catch (NotFoundException e) {}
        return nbResa;
    }

    protected Boolean isReservable(EnCoursBean enCours, int nbResa){
        Boolean isReservable = false;
        int maxResaPossible = 2 * enCours.getNbEncours();
        if (maxResaPossible > nbResa){
            isReservable = true;
        }
        return isReservable;
    }

    protected BibliothequeBean choixBibliotheque() {
        BibliothequeBean bibliothequeChoisie = new BibliothequeBean();
        LOGGER.debug("choixBibliotheque");
        try {
            bibliothequeChoisie.setBibliothequeId(Long.parseLong(request.getSession().getAttribute("bibliotheque").toString()));
        } catch (Exception e) {
            bibliothequeChoisie.setBibliothequeId(null);
        }
        return bibliothequeChoisie;
    }
}
