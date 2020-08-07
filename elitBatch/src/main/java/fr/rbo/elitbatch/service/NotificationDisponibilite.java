package fr.rbo.elitbatch.service;

import fr.rbo.elitbatch.beans.ReservationBean;
import fr.rbo.elitbatch.exceptions.NotFoundException;
import fr.rbo.elitbatch.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationDisponibilite {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDisponibilite.class);

    @Autowired
    private APIProxy apiProxy;
    @Autowired
    private EmailService emailService;

    /**
     * Itère sur la liste des reservations à notifier
     * Et pour chaque cas envoi un email à l'adhérent concerné
     */
    public void mailsDeNotifications() {
        LOGGER.info("Début du traitement : mailsDeNotifications");
        Date date = new Date();
        System.out.println("Passage du batch d'envoi des mails de notifications de disponibilites - " + date.toString());
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.listeDesReservationsaNotifier();
        } catch(NotFoundException e){}

        if (reservations != null){
            for (ReservationBean reservation : reservations)  {
                System.out.println( "reservation : " + reservation.getReservationId() + " user : " + reservation.getUser().getEmail());
                try {
                    emailService.envoiEmailNotification(reservation);
                    apiProxy.majDateNotificationById((int) reservation.getReservationId());
                } catch(NotFoundException e){}
            }
        }
    }
}

