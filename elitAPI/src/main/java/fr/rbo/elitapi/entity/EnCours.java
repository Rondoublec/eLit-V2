package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class EnCours {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnCours.class);

    private Long ouvrageId;
    private int nbEncours;
    private Date dateRetourPrevue;

    public EnCours(Long ouvrageId, int nbEncours, Date dateRetourPrevue) {
        this.ouvrageId = ouvrageId;
        this.nbEncours = nbEncours;
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public Long getOuvrageId() {
        return ouvrageId;
    }

    public void setOuvrageId(Long ouvrageId) {
        this.ouvrageId = ouvrageId;
    }

    public int getNbEncours() {
        return nbEncours;
    }

    public void setNbEncours(int nbEncours) {
        this.nbEncours = nbEncours;
    }

    public Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
}
