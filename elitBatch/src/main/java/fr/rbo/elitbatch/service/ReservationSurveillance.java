package fr.rbo.elitbatch.service;

import fr.rbo.elitbatch.beans.ReservationBean;
import fr.rbo.elitbatch.exceptions.NotFoundException;
import fr.rbo.elitbatch.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationSurveillance {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSurveillance.class);

    @Value("${duree.delaisAttente}")
    private int delaisAttente;

    @Autowired
    private APIProxy apiProxy;

    public void gestionDesReservations() {
        LOGGER.info("Début du traitement : gestionDesReservations");
        Date date = new Date();
        LOGGER.info("Passage du batch de surveillance desréservations - " + date.toString());
        // remonter toutes les demandes de réservations actives & notifiées
        List<ReservationBean> reservations = null;
        try {
            reservations = apiProxy.listeDesReservationsaNotifiees();
        } catch(NotFoundException e){}

        long intervalle = 0;
        if (reservations != null){
            for (ReservationBean reservation : reservations)  {
                LOGGER.info( "reservation : " + reservation.getReservationId() + " date de notifications : " + reservation.getReservationDateNotif());
                intervalle = date.getTime() - reservation.getReservationDateNotif().getTime();
                LOGGER.info( "Délais                           : " + TimeUnit.HOURS.convert(intervalle, TimeUnit.MILLISECONDS));
                // si la notification de la réservation a plus de xx heures (variable duree.delaisAttente)
                if ((TimeUnit.HOURS.convert(intervalle, TimeUnit.MILLISECONDS) >= delaisAttente )) {
                    try {
                        LOGGER.info( "Fermer la demande de réservation : " + reservation.getReservationId());
                        apiProxy.switchEtatReservationById(reservation.getReservationId());
                        LOGGER.info( "Fermer la demande de réservation : FAIT");
                    } catch(NotFoundException e){}
                    try {
                        LOGGER.info( "Flaguer [à notifier] la réservation suivante pour l'ouvrage : " + reservation.getOuvrage().getOuvrageId());
                        apiProxy.majReservationaNotifierById(reservation.getOuvrage().getOuvrageId());
                        LOGGER.info( "Flaguer [à notifier] la réservation suivante pour l'ouvrage : FAIT");
                    } catch(NotFoundException e){}
                }
            }
        }
    }
}