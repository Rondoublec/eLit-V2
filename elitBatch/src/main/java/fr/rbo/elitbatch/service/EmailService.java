package fr.rbo.elitbatch.service;

import fr.rbo.elitbatch.beans.EmpruntBean;
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
        LOGGER.info("Envoi mail");
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
}
