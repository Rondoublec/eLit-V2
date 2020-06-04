package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.BibliothequeBean;
import fr.rbo.elitweb.beans.OuvrageBean;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OuvragesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OuvragesController.class);

    @Autowired
    APIProxy apiProxy;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/ouvrages", method = RequestMethod.GET)
    public String Ouvrages(Model model, HttpSession httpSession
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /ouvrages");
        OuvrageBean ouvrageCriteres = new OuvrageBean();
        ouvrageCriteres.setBibliotheque(choixBibliotheque());
        try {
            if (ouvrageCriteres.getBibliotheque().getBibliothequeId().toString().isEmpty()) { return "redirect:/bibliotheques"; }}
        catch (Exception e) { return "redirect:/bibliotheques"; }
        List<OuvrageBean> ouvrages = null;
        try {
            ouvrages = apiProxy.rechercheOuvrage(ouvrageCriteres); }
        catch (NotFoundException e) {}
        model.addAttribute("titre", "Les plus populaires !");
        model.addAttribute("ouvrageCriteres", ouvrageCriteres);
        model.addAttribute("ouvrages", ouvrages);
        return "recherche-ouvrages-list";
    }

    @RequestMapping(value = "/ouvrages/recherche", method = RequestMethod.POST)
    public String OuvragesRecherche(Model model,
                                    @ModelAttribute("ouvrageCriteres") OuvrageBean ouvrageCriteres,
                                    HttpSession httpSession) {
        LOGGER.debug("Post /ouvrages/recherche");
        ouvrageCriteres.setBibliotheque(choixBibliotheque());
        try {
            if (ouvrageCriteres.getBibliotheque().getBibliothequeId().toString().isEmpty()) { return "redirect:/bibliotheques"; }}
        catch (Exception e) { return "redirect:/bibliotheques"; }
        List<OuvrageBean> ouvrages = null;
        try {
            ouvrages = apiProxy.rechercheOuvrage(ouvrageCriteres);
        } catch (NotFoundException e) {
        }
        model.addAttribute("titre", "Résultats de la recherche");
        model.addAttribute("ouvrageCriteres", ouvrageCriteres);
        model.addAttribute("ouvrages", ouvrages);
        return "recherche-ouvrages-list";
    }

    @RequestMapping(value = "/ouvrage/details", method = RequestMethod.GET)
    public String details(@RequestParam("ouvrageId") int ouvrageId, Model model
            , final RedirectAttributes redirectAttributes) {
        LOGGER.debug("Get /ouvrages/details ouvrageId : " + ouvrageId);
        OuvrageBean ouvrage = null;
        try {
            ouvrage = apiProxy.findOuvrageById(ouvrageId);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("status", "notFound");
            model.addAttribute("status", "notFound");
            return "redirect:/ouvrages";
        }
        model.addAttribute("ouvrage", ouvrage);
        return "details-ouvrage";
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
