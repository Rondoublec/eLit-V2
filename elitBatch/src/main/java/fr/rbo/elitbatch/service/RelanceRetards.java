package fr.rbo.elitbatch.service;

import fr.rbo.elitweb.beans.EmpruntBean;
import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitbatch.exceptions.NotFoundException;
import fr.rbo.elitbatch.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RelanceRetards {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelanceRetards.class);


    @Autowired
    private APIProxy apiProxy;
    @Autowired
    private EmailService emailService;
    private final ClientAPIService clientService;

    public RelanceRetards(ClientAPIService clientService) {
        this.clientService = clientService;
    }

    /**
     * Itère sur la liste des adhérents des bibiliothèques
     * Et pour chaque adhérent va rechercher les ouvrages en retard, emprunt en cours dont la date de restitution (prolongation comprise) est dépassée
     * Et envoi un email aux adhérents concernés
     */
    public void mailsDeRelances() {
        LOGGER.info("Début du traitement : mailsDeRelances");
        Date date = new Date();
        LOGGER.info("Passage du batch d'envoi des mails de relances - " + date.toString());
        List<UserBean> listeUser = clientService.listeUser();
        for (UserBean user : listeUser)  {
        LOGGER.info( "user : " + user.getId());
            List<EmpruntBean> emprunts = null;
            try {
                emprunts = apiProxy.listeDesEmpruntsEnRetard(user.getId());
                if (!emprunts.isEmpty()) {
                    emailService.envoiEmailRelance(user, emprunts);
                    //TODO Créer un champs Nb relance et l'incrémenter
                    //TODO mettre à jour la date de relance sur les emprunts
                    //TODO ne relancer qu'une sois par semaine
                }
            } catch(NotFoundException e){}
        }
    }

}
