package fr.rbo.elitweb.beans;

public class BibliothequeBean {

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

    @Override
    public String toString() {
        return "BibliothequeBean{" +
                "bibliothequeId=" + bibliothequeId +
                ", bibliothequeNom='" + bibliothequeNom + '\'' +
                ", bibliothequeAdresse='" + bibliothequeAdresse + '\'' +
                ", bibliothequeHoraires='" + bibliothequeHoraires + '\'' +
                ", bibliothequeTelephone='" + bibliothequeTelephone + '\'' +
                ", bibliothequeEmail='" + bibliothequeEmail + '\'' +
                '}';
    }

    public String infos() {
        return bibliothequeNom
                + ' ' + bibliothequeAdresse
                + ' ' + bibliothequeHoraires
                + ' ' + bibliothequeTelephone
                + ' ' + bibliothequeEmail
                ;
    }



}
