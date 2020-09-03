package fr.rbo.elitbatch;

import fr.rbo.elitbatch.scheduledtask.PlanificationBatchGestionReservations;
import fr.rbo.elitbatch.scheduledtask.PlanificationBatchRelanceRetards;
import fr.rbo.elitbatch.beans.EmpruntBean;
import fr.rbo.elitbatch.beans.OuvrageBean;
import fr.rbo.elitbatch.beans.ReservationBean;
import fr.rbo.elitbatch.beans.UserBean;
import fr.rbo.elitbatch.proxies.APIProxy;
import fr.rbo.elitbatch.service.EmailService;
import fr.rbo.elitbatch.service.NotificationDisponibilite;
import fr.rbo.elitbatch.service.RelanceRetards;
import fr.rbo.elitbatch.service.ReservationSurveillance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ElitBatchApplicationTest {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private APIProxy apiProxy;
    @Autowired
    private RelanceRetards relanceRetards;
    @Autowired
    private NotificationDisponibilite notificationDisponibilite;
    @Autowired
    private ReservationSurveillance reservationSurveillance;

    private EmailService emailService;
    @Autowired
    private PlanificationBatchRelanceRetards planificationBatchRelanceRetards;
    @Autowired
    private PlanificationBatchGestionReservations planificationBatchGestionReservations;

    @Test
    public void testRien() {

    }
    // @Test
    public void testTraitementRelance() {
        relanceRetards.mailsDeRelances();
    }
    // @Test
    public void testPlanificationRelances(){
        planificationBatchRelanceRetards.planificationBatchRelanceRetardsCron();
    }
    // @Test
    public void testEnvoiMail() {
        emailService = new EmailService(mailSender);
        UserBean user = new UserBean();
        user.setEmail("user@a.a");
        user.setName("Rémy");
        user.setLastName("Toto");
        List<EmpruntBean> listeEmprunt = new ArrayList<>();
        EmpruntBean emprunt = new EmpruntBean();
        OuvrageBean ouvrage = new OuvrageBean();
        ouvrage.setOuvrageAuteur("Auteur");
        ouvrage.setOuvrageTitre("Titre");
        emprunt.setOuvrage(ouvrage);
        emprunt.setEmpruntDateFin(new Date());
        listeEmprunt.add(emprunt);
        listeEmprunt.add(emprunt);
        emailService.envoiEmailRelance(user, listeEmprunt);
    }

    // @Test
    public void testTraitementReservation() { reservationSurveillance.gestionDesReservations();}
    // @Test
    public void testTraitementNotification() {
        notificationDisponibilite.mailsDeNotifications();
    }
    // @Test
    public void testPlanificationNotification(){
        planificationBatchGestionReservations.planificationBatchReservationEtNotificationCron();
    }
    // @Test
    public void testEnvoiMailNotif() {
        emailService = new EmailService(mailSender);
        ReservationBean reservation = new ReservationBean();
        UserBean user = new UserBean();
        user.setEmail("user@a.a");
        user.setName("Rémy");
        user.setLastName("Toto");
        reservation.setUser(user);
        OuvrageBean ouvrage = new OuvrageBean();
        ouvrage.setOuvrageAuteur("Auteur");
        ouvrage.setOuvrageTitre("Titre");
        reservation.setOuvrage(ouvrage);
        reservation.setReservationDateDemande(new Date());
        emailService.envoiEmailNotification(reservation);
    }

}