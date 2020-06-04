package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bibliotheque {
    private static final Logger log = LoggerFactory.getLogger(Bibliotheque.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bibliothequeId;
    private String bibliothequeNom;
    private String bibliothequeAdresse;
    private String bibliothequeHoraires;
    private String bibliothequeTelephone;
    private String bibliothequeEmail;

    public Long getBibliothequeId() {
        return bibliothequeId;
    }

    public void setBibliothequeId(Long bibliothequeId) {
        this.bibliothequeId = bibliothequeId;
    }

    public String getBibliothequeNom() {
        return bibliothequeNom;
    }

    public void setBibliothequeNom(String bibliothequeNom) {
        this.bibliothequeNom = bibliothequeNom;
    }

    public String getBibliothequeAdresse() {
        return bibliothequeAdresse;
    }

    public void setBibliothequeAdresse(String bibliothequeAdresse) {
        this.bibliothequeAdresse = bibliothequeAdresse;
    }

    public String getBibliothequeHoraires() {
        return bibliothequeHoraires;
    }

    public void setBibliothequeHoraires(String bibliothequeHoraires) {
        this.bibliothequeHoraires = bibliothequeHoraires;
    }

    public String getBibliothequeTelephone() {
        return bibliothequeTelephone;
    }

    public void setBibliothequeTelephone(String bibliothequeTelephone) {
        this.bibliothequeTelephone = bibliothequeTelephone;
    }

    public String getBibliothequeEmail() {
        return bibliothequeEmail;
    }

    public void setBibliothequeEmail(String bibliothequeEmail) {
        this.bibliothequeEmail = bibliothequeEmail;
    }
}
