package fr.rbo.elitbatch.beans;

import java.util.Date;

public class EmpruntBean {

    private long empruntId;
    private OuvrageBean ouvrage;
    private UserBean user;
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
