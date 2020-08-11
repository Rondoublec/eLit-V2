package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.exceptions.NotAcceptableException;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.OuvrageRepository;
import fr.rbo.elitapi.repository.OuvrageRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OuvrageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageController.class);

    @Autowired
    OuvrageRepository ouvrageRepository;
    @Autowired
    OuvrageRepositoryInterface ouvrageRecherche;

    /**
     * renvoie la liste de tous les ouvrages
     * @return la liste de tous les ouvrages
     */
    @GetMapping(value="/ouvrages")
    public List<Ouvrage> listeDesOuvrages(){
        LOGGER.debug("Get /ouvrages");
        List<Ouvrage> ouvrages = ouvrageRepository.findAll();
        if (ouvrages.isEmpty()) throw new NotFoundException("Il n'y a pas d'ouvrage correspondant à votre recherche");
        return ouvrages;
    }

    /**
     * renvoie la liste des ouvrages correspondants aux critères de recherche
     * @param ouvrageCherche critères de recherche
     * @return la liste des ouvrages correspondants aux critères de recherche
     */
    @PostMapping(value="/ouvrages/recherche")
    public List<Ouvrage> listeDesOuvragesSelonCriteres( @RequestBody Ouvrage ouvrageCherche){
        LOGGER.debug("Post /ouvrages/recherche");
        List<Ouvrage> ouvrages = ouvrageRecherche.rechercheOuvrage(ouvrageCherche);
        if (ouvrages.isEmpty()) throw new NotFoundException("Il n'y a pas d'ouvrage correspondant à votre recherche");
        return ouvrages;
    }

    /**
     * renvoie les informations détailes de l'ouvrage correspondant à l'id
     * @param id de l'ouvrage
     * @return les informations détailes de l'ouvrage correspondant à l'id
     */
    @GetMapping(value = "/ouvrage/{id}")
    public Ouvrage recupererUnOuvrage (@PathVariable("id") Long id){
        LOGGER.debug("Get /ouvrage/{id} " + id);
        return ouvrageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cet ouvrage n'existe pas"));
    }

    /**
     * crée un ouvrage avec les informations contenues dans le flux
     * @param ouvrage données
     * @return ouvrage
     */
    @PostMapping(value = "/ouvrage/ajout")
    public Ouvrage creerOuvrage (@RequestBody Ouvrage ouvrage){
        LOGGER.debug("Post /ouvrage/ajout");
        if (ouvrage.getOuvrageId() != null) throw new NotAcceptableException("Demande fausse, ouvrageId présent");
        ouvrageRepository.save(ouvrage);
        return ouvrage;
    }

    /**
     * met à jour les informations de l'ouvrage correspondant à l'id avec les informations contenues dans le flux
     * @param id de l'ouvrage
     * @param ouvrageNew données
     * @return ouvrage
     */
    @PutMapping(value = "/ouvrage/maj/{id}")
    public Ouvrage mettreAJourOuvrage (@PathVariable("id") Long id,
                                        @RequestBody Ouvrage ouvrageNew){
        LOGGER.debug("Put /ouvrage/maj/{id} " + id);
        if (ouvrageNew.getOuvrageId() != null) {
            if (!ouvrageNew.getOuvrageId().equals(id)) throw new NotAcceptableException("Demande fausse, ouvrageId différent");
        }
        Ouvrage ouvrage = ouvrageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Ouvrage inexistant, non trouvé"));
        if (ouvrageNew.getOuvrageReference() != null) ouvrage.setOuvrageReference(ouvrageNew.getOuvrageReference());
        if (ouvrageNew.getOuvrageTitre() != null) ouvrage.setOuvrageTitre(ouvrageNew.getOuvrageTitre());
        if (ouvrageNew.getOuvrageAuteur() != null) ouvrage.setOuvrageAuteur(ouvrageNew.getOuvrageAuteur());
        if (ouvrageNew.getOuvrageStyle() != null) ouvrage.setOuvrageStyle(ouvrageNew.getOuvrageStyle());
        if (ouvrageNew.getOuvrageResume() != null) ouvrage.setOuvrageResume(ouvrageNew.getOuvrageResume());
        if (ouvrageNew.getOuvrageLocalisation() != null) ouvrage.setOuvrageLocalisation(ouvrageNew.getOuvrageLocalisation());
        if (ouvrageNew.getOuvrageQuantite() != null) ouvrage.setOuvrageQuantite(ouvrageNew.getOuvrageQuantite());
        ouvrageRepository.save(ouvrage);
        return ouvrage;
    }

}
