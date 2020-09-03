package fr.rbo.elitbatch.beans;

public class OuvrageBean {

    private Long ouvrageId;
    private String ouvrageReference;
    private String ouvrageTitre;
    private String ouvrageAuteur;
    private String ouvrageStyle;
    private String ouvrageResume;
    private String ouvrageLocalisation;
    private String ouvrageQuantite;
    private String ouvrageLienImage;
    private BibliothequeBean bibliotheque;

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

    public BibliothequeBean getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(BibliothequeBean bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

}
