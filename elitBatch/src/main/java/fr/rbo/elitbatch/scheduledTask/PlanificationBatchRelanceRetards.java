package fr.rbo.elitbatch.scheduledTask;

import fr.rbo.elitbatch.service.ClientAPIService;
import fr.rbo.elitbatch.service.RelanceRetards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PlanificationBatchRelanceRetards {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanificationBatchRelanceRetards.class);

    @Autowired
    private RelanceRetards relanceRetards;

    private final ClientAPIService clientService;

    public PlanificationBatchRelanceRetards(ClientAPIService clientService) {
        this.clientService = clientService;
    }

    /**
     * Planification du batch de relance des retards de restitutions d'emprunts
     * Paramétrage de @Scheduled(cron = "* * * * * * *")
     *                                   * seconde (0-59)
     *                                     * minute (0-59)
     *                                       * heure (0-23)
     *                                         * jour du mois (1-31)
     *                                           * mois (1-12)
     *                                             * jour de la semaine (0-6) 0=lundi
     *                                              * année
     * exemples :
     * A 23 heure du lundi au vendredi cron = "0 0 23 * * 0-4"
     * A 23 heure tous les jours cron = "0 0 23 * * ?"
     */
    // parametre dans le application.properties / toutes les 2 minutes pour les besoins du test
    @Scheduled(cron = "${batch.cron.value}")
    public void planificationBatchRelanceRetardsCron() {
        LOGGER.info("Lancement du batch");
        System.out.println( "DEBUT : Appel du traitement des relances ========================== ");
        relanceRetards.mailsDeRelances();
        System.out.println( " FIN  : Appel du traitement des relances ========================== ");

    }
}
