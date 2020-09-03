package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Ouvrage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ouvrage.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ouvrageId;
    private String ouvrageReference;
    private String ouvrageTitre;
    private String ouvrageAuteur;
    private String ouvrageStyle;
    private String ouvrageResume;
    private String ouvrageLocalisation;
    private String ouvrageQuantite;
    private String ouvrageLienImage;
    @OneToOne
    private Bibliotheque bibliotheque;

    public Long getOuvrageId() {
        return ouvrageId;
    }

    public void setOuvrageId(Long ouvrageId) {
        this.ouvrageId = ouvrageId;
    }

    public String getOuvrageReference() {
        return ouvrageReference;
    }

    public void setOuvrageReference(String ouvrageReference) {
        this.ouvrageReference = ouvrageReference;
    }

    public String getOuvrageTitre() {
        return ouvrageTitre;
    }

    public void setOuvrageTitre(String ouvrageTitre) {
        this.ouvrageTitre = ouvrageTitre;
    }

    public String getOuvrageAuteur() {
        return ouvrageAuteur;
    }

    public void setOuvrageAuteur(String ouvrageAuteur) {
        this.ouvrageAuteur = ouvrageAuteur;
    }

    public String getOuvrageStyle() {
        return ouvrageStyle;
    }

    public void setOuvrageStyle(String ouvrageStyle) {
        this.ouvrageStyle = ouvrageStyle;
    }

    public String getOuvrageResume() {
        return ouvrageResume;
    }

    public void setOuvrageResume(String ouvrageResume) {
        this.ouvrageResume = ouvrageResume;
    }

    public String getOuvrageLocalisation() {
        return ouvrageLocalisation;
    }

    public void setOuvrageLocalisation(String ouvrageLocalisation) {
        this.ouvrageLocalisation = ouvrageLocalisation;
    }

    public String getOuvrageQuantite() {
        return ouvrageQuantite;
    }

    public void setOuvrageQuantite(String ouvrageQuantite) {
        this.ouvrageQuantite = ouvrageQuantite;
    }

    public String getOuvrageLienImage() {
        return ouvrageLienImage;
    }

    public void setOuvrageLienImage(String ouvrageLienImage) {
        this.ouvrageLienImage = ouvrageLienImage;
    }

    public Bibliotheque getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
    }
}
