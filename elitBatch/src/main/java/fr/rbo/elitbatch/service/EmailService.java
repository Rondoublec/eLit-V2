package fr.rbo.elitbatch.service;

import fr.rbo.elitbatch.beans.EmpruntBean;
import fr.rbo.elitbatch.beans.ReservationBean;
import fr.rbo.elitbatch.beans.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envoi un mail avec la liste des prets en retard à l'user concerné
     * @param user utilisateur
     * @param listeEmprunt liste des emprunts
     */
    public void envoiEmailRelance(UserBean user, List<EmpruntBean> listeEmprunt) {
        LOGGER.info("Envoi mail de relance");
        StringBuilder listeDesEmprunts = new StringBuilder();
        SimpleDateFormat formater = new SimpleDateFormat("'le' dd/MM/yyyy");

        String newLine = System.getProperty("line.separator");

        for (EmpruntBean emprunt : listeEmprunt) {
            listeDesEmprunts.append(" -  " + emprunt.getOuvrage().getOuvrageTitre()
                    + " de " + emprunt.getOuvrage().getOuvrageAuteur() + ", "
                    + " à rendre au plus tard le " + formater.format(emprunt.getEmpruntDateFin()) + ". " + newLine);
        }
        String subject = "Date de retour de prêt dépassée !";
        String body = "Bonjour " + user.getName() + " " + user.getLastName() + ", "
                + newLine + newLine +"La date de fin de vos emprunts est dépasée pour le ou les ouvrage(s) suivant(s) : "
                + newLine + listeDesEmprunts
                + newLine + "Pour ne pas pénaliser les autres usagers, nous vous demandons de les restituer dans les meilleurs délais."
                + newLine + newLine + "Votre bibliothèque municipale";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("noreply@nm.fr");
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }

    /**
     * Envoi un mail de notification de disponiblite de l'ouvrage réservé à l'user concerné
     * @param reservation réservation
     */
    public void envoiEmailNotification(ReservationBean reservation) {
        LOGGER.info("Envoi mail de notification");

        SimpleDateFormat formater = new SimpleDateFormat("'le' dd/MM/yyyy");
        String newLine = System.getProperty("line.separator");

        String subject = "Votre ouvrage réservé est disponible.";
        String body = "Bonjour " + reservation.getUser().getName() + " " + reservation.getUser().getLastName() + ", "
                + newLine + newLine +"L'ouvrage " + reservation.getOuvrage().getOuvrageTitre() + " "
                + newLine + "que vous avez reservé " + formater.format(reservation.getReservationDateDemande())
                + " est disponible dans votre bibliothèque municipale."
                + newLine + newLine + "Pour ne pas pénaliser les autres usagers."
                + newLine + "Il sera conservé 48 heures après la date de cette notification."
                + newLine + "Passé ce délais, votre réservation sera annulée et l'ouvrage sera mis à disposition de la personne suivante."
                + newLine + newLine + "Votre bibliothèque municipale";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(reservation.getUser().getEmail());
        mailMessage.setFrom("noreply@nm.fr");
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }
}
