package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.EmpruntBean;
import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitweb.exceptions.NotAcceptableException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class EmpruntController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntController.class);

    private static final String REDIRECT_MESEMPRUNTS = "redirect:/mesemprunts";
    
    @Autowired
    APIProxy apiProxy;

    /**
     * Affiche la liste de emprunts en cours (non rendus) de l'utilisateur connecté
     * @param model model
     * @param httpSession Session
     * @param redirectAttributes attributs valorisés de la redirection
     * @return liste de emprunts en cours (non rendus) de l'utilisateur connecté
     */
    @GetMapping(path = "/mesemprunts")
    public String MesEmprunts(Model model, HttpSession httpSession
            ,final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /mesemprunts");
        EmpruntBean empruntCriteres = new EmpruntBean();
        empruntCriteres.setUser(recupUser());
        empruntCriteres.setEmpruntRendu(false);
        List<EmpruntBean> emprunts = null;
        try {
            emprunts = apiProxy.rechercheEmpruntCriteres(empruntCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("datedujour",Calendar.getInstance().getTime());
        model.addAttribute("titre", "Mes emprunts");
        model.addAttribute("empruntCriteres", empruntCriteres);
        model.addAttribute("emprunts", emprunts);
        return "recherche-emprunts-list";
    }
    /**
     * Affiche la liste de emprunts de l'utilisateur connecté correspondants aux critères de recherche
     * @param model model
     * @param empruntCriteres citères de recherche
     * @param httpSession Session
     * @return liste de emprunts de l'utilisateur connecté correspondants aux critères de recherche
     */
    @PostMapping(path = "/mesemprunts")
    public String EmpruntsRecherche (Model model, HttpSession httpSession,
                                     @ModelAttribute("empruntCriteres") EmpruntBean empruntCriteres) {
        LOGGER.debug("Post /mesemprunts");
        empruntCriteres.setUser(recupUser());
        List<EmpruntBean> emprunts = null;
        try {
            emprunts = apiProxy.rechercheEmpruntCriteres(empruntCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("datedujour",Calendar.getInstance().getTime());
        model.addAttribute("titre", "Résultats de la recherche");
        model.addAttribute("empruntCriteres", empruntCriteres);
        model.addAttribute("emprunts", emprunts);
        return "recherche-emprunts-list";
    }

    /**
     * Affiche les informations détaillées d'un emprunt
     * @param empruntId de l'emprunt
     * @param model model
     * @param redirectAttributes attributs valorisés de la redirection
     * @return informations détaillées de l'emprunt
     */
    @GetMapping(path = "/emprunt/details")
    public String details(@RequestParam("empruntId") int empruntId, Model model
            ,final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /emprunt/details empruntId : " + empruntId);
        EmpruntBean emprunt = null;
        try {
            emprunt = apiProxy.findEmpruntById(empruntId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("status","notFound");
            model.addAttribute("status", "notFound");
            return REDIRECT_MESEMPRUNTS;
        }
        if (!emprunt.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            model.addAttribute("status","notAuthorize");
            return REDIRECT_MESEMPRUNTS;
        }
        model.addAttribute("emprunt", emprunt);
        return "details-emprunt";
    }

    /**
     * Prolonge un emprunt en cours pour 4 semaines supplémentaires, (règles portées par l'API)
     * @param empruntId de l'emprunt
     * @param model model
     * @param redirectAttributes attributs valorisés de la redirection
     * @return redirection vers la liste des emprunts rafraichie
     */
    @GetMapping(path = "/emprunt/plus")
    public String plus(@RequestParam("empruntId") int empruntId, Model model
            ,final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /emprunt/plus empruntId : " + empruntId);
        EmpruntBean emprunt = null;
        try {
            emprunt = apiProxy.findEmpruntById(empruntId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("status","notFound");
            model.addAttribute("status", "notFound");
            return REDIRECT_MESEMPRUNTS;
        }
        if (!emprunt.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            model.addAttribute("status","notAuthorize");
            return REDIRECT_MESEMPRUNTS;
        }
        try {
            emprunt = apiProxy.prolongeEmpruntById(empruntId);
            redirectAttributes.addFlashAttribute("plus","success");
            model.addAttribute("plus", "success");
        } catch(NotAcceptableException e){
            redirectAttributes.addFlashAttribute("plus","unsuccess");
            model.addAttribute("plus", "unsuccess");
        }
        return REDIRECT_MESEMPRUNTS;
    }

    private UserBean recupUser(){
        UserBean userCritere = new UserBean();
        userCritere.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        LOGGER.debug("recupUser critere email : " + userCritere.getEmail());
        return userCritere;
    }

}
