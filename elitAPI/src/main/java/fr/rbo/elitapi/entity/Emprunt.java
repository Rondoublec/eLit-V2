package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Emprunt{
    private static final Logger log = LoggerFactory.getLogger(Emprunt.class);

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long empruntId;
        @ManyToOne
        private Ouvrage ouvrage;
        @ManyToOne
        private User user;
        private Date empruntDateDebut;
        private Date empruntDateFin;
        private Date empruntDateRetour;
        private Date empruntDateProlongation;
        private Date empruntDateRelance;
        private Boolean empruntProlongation;
        private Boolean empruntRendu;
        private Boolean empruntRelance;

    public long getEmpruntId() {
        return empruntId;
    }

    public void setEmpruntId(long empruntId) {
        this.empruntId = empruntId;
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

    public Date getEmpruntDateDebut() {
        return empruntDateDebut;
    }

    public void setEmpruntDateDebut(Date empruntDateDebut) {
        this.empruntDateDebut = empruntDateDebut;
    }

    public Date getEmpruntDateFin() {
        return empruntDateFin;
    }

    public void setEmpruntDateFin(Date empruntDateFin) {
        this.empruntDateFin = empruntDateFin;
    }

    public Date getEmpruntDateRetour() {
        return empruntDateRetour;
    }

    public void setEmpruntDateRetour(Date empruntDateRetour) {
        this.empruntDateRetour = empruntDateRetour;
    }

    public Date getEmpruntDateProlongation() {
        return empruntDateProlongation;
    }

    public void setEmpruntDateProlongation(Date empruntDateProlongation) {
        this.empruntDateProlongation = empruntDateProlongation;
    }

    public Date getEmpruntDateRelance() {
        return empruntDateRelance;
    }

    public void setEmpruntDateRelance(Date empruntDateRelance) {
        this.empruntDateRelance = empruntDateRelance;
    }

    public Boolean getEmpruntProlongation() {
        return empruntProlongation;
    }

    public void setEmpruntProlongation(Boolean empruntProlongation) {
        this.empruntProlongation = empruntProlongation;
    }

    public Boolean getEmpruntRendu() {
        return empruntRendu;
    }

    public void setEmpruntRendu(Boolean empruntRendu) {
        this.empruntRendu = empruntRendu;
    }

    public Boolean getEmpruntRelance() {
        return empruntRelance;
    }

    public void setEmpruntRelance(Boolean empruntRelance) {
        this.empruntRelance = empruntRelance;
    }
}
