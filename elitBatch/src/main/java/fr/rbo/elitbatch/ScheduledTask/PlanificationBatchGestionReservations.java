package fr.rbo.elitbatch.ScheduledTask;

import fr.rbo.elitbatch.service.NotificationDisponibilite;
import fr.rbo.elitbatch.service.RelanceRetards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PlanificationBatchGestionReservations {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanificationBatchGestionReservations.class);

    @Autowired
    private NotificationDisponibilite notificationDisponibilite;

    // parametre dans le application.properties / toutes les 2 minutes pour les besoins du test
    @Scheduled(cron = "${batch.cron.value}")
    public void PlanificationBatchNotificationCron() {
        LOGGER.info("Lancement du batch d'envoi des notifications");
        System.out.println("DEBUT : Appel du traitement des envois de mail de notification de disponibilité === ");
        notificationDisponibilite.mailsDeNotifications();
        System.out.println(" FIN  : Appel du traitement des envois de mail de notification de disponibilité === ");

    }

}
