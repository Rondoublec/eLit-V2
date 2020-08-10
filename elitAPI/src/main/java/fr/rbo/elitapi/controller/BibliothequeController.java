package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Bibliotheque;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.BibliothequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BibliothequeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BibliothequeController.class);

    @Autowired
    BibliothequeRepository bibliothequeRepository;

    /**
     * Restitue la liste des bibliothèques
     * @return liste des bibliothèques
     */
    @GetMapping(value="/bibliotheques")
    public List<Bibliotheque> listeDesBibliotheques(){
        LOGGER.debug("Get /bibliotheques");
        List<Bibliotheque> bibliotheques = bibliothequeRepository.findAll();
        if (bibliotheques.isEmpty()) throw new NotFoundException("Il n'y a pas de bibliothèque correspondant à votre recherche");
        return bibliotheques;
    }

}
