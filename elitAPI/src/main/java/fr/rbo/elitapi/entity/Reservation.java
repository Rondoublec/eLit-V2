package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Reservation {
    private static final Logger log = LoggerFactory.getLogger(Reservation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reservationId;
    @ManyToOne
    private Ouvrage ouvrage;
    @ManyToOne
    private User user;
    private Date reservationDateDemande;
    private Date reservationDateNotif;
    @NotNull
    private Boolean reservationActive;
    private Boolean notifier;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReservationDateDemande() {
        return reservationDateDemande;
    }

    public void setReservationDateDemande(Date reservationDateDemande) {
        this.reservationDateDemande = reservationDateDemande;
    }

    public Date getReservationDateNotif() {
        return reservationDateNotif;
    }

    public void setReservationDateNotif(Date reservationDateNotif) {
        this.reservationDateNotif = reservationDateNotif;
    }

    public Boolean getReservationActive() {
        return reservationActive;
    }

    public void setReservationActive(Boolean reservationActive) {
        this.reservationActive = reservationActive;
    }

    public Boolean getNotifier() {
        return notifier;
    }

    public void setNotifier(Boolean notifier) {
        this.notifier = notifier;
    }

}
