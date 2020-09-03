package fr.rbo.elitweb.beans;

import java.util.Date;

public class ReservationBean {

    private long reservationId;
    private OuvrageBean ouvrage;
    private UserBean user;
    private Date reservationDateDemande;
    private Date reservationDateNotif;
    private Boolean reservationActive;
    private Boolean notifier;
    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public OuvrageBean getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(OuvrageBean ouvrage) {
        this.ouvrage = ouvrage;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
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
