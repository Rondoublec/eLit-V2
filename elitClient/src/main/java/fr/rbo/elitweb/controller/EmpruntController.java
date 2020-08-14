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
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    APIProxy apiProxy;

    /**
     * Affiche la liste de emprunts en cours (non rendus) de l'utilisateur connecté
     * @param model model
     * @param httpSession Session
     * @param redirectAttributes attributs valorisés de la redirection
     * @return liste de emprunts en cours (non rendus) de l'utilisateur connecté
     */
    @RequestMapping(value="/mesemprunts", method = RequestMethod.GET)
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
    @RequestMapping(value="/mesemprunts/recherche", method = RequestMethod.POST)
    public String EmpruntsRecherche (Model model,
                                     @ModelAttribute("empruntCriteres") EmpruntBean empruntCriteres,
                                     HttpSession httpSession) {
        LOGGER.debug("Post /mesemprunts/recherche");
        List<EmpruntBean> emprunts = null;
        empruntCriteres.setUser(recupUser());
        try {
            emprunts = apiProxy.rechercheEmpruntCriteres(empruntCriteres);
        } catch(NotFoundException e){}
        model.addAttribute("datedujour",Calendar.getInstance().getTime());
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
    @RequestMapping(value = "/emprunt/details", method = RequestMethod.GET)
    public String details(@RequestParam("empruntId") int empruntId, Model model
            ,final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /emprunt/details empruntId : " + empruntId);
        EmpruntBean emprunt = null;
        try {
            emprunt = apiProxy.findEmpruntById(empruntId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("status","notFound");
            model.addAttribute("status", "notFound");
            return "redirect:/mesemprunts";
        }
        if (!emprunt.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            model.addAttribute("status","notAuthorize");
            return "redirect:/mesemprunts";
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
    @RequestMapping(value = "/emprunt/plus", method = RequestMethod.GET)
    public String plus(@RequestParam("empruntId") int empruntId, Model model
            ,final RedirectAttributes redirectAttributes){
        LOGGER.debug("Get /emprunt/plus empruntId : " + empruntId);
        EmpruntBean emprunt = null;
        try {
            emprunt = apiProxy.findEmpruntById(empruntId);
        } catch(NotFoundException e){
            redirectAttributes.addFlashAttribute("status","notFound");
            model.addAttribute("plus", "notFound");
            return "redirect:/mesemprunts";
        }
        if (!emprunt.getUser().getEmail().equals(recupUser().getEmail())){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            model.addAttribute("status","notAuthorize");
            return "redirect:/mesemprunts";
        }
        try {
            emprunt = apiProxy.prolongeEmpruntById(empruntId);
            redirectAttributes.addFlashAttribute("plus","success");
            model.addAttribute("plus", "success");
        } catch(NotAcceptableException e){
            redirectAttributes.addFlashAttribute("plus","unsuccess");
            model.addAttribute("plus", "unsuccess");
        }
        return "redirect:/mesemprunts";
    }

    private UserBean recupUser(){
        UserBean userCritere = new UserBean();
        userCritere.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        LOGGER.debug("recupUser critere email : " + userCritere.getEmail());
        return userCritere;
    }

}
