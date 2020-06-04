package fr.rbo.elitbatch;

import fr.rbo.elitbatch.ScheduledTask.PlanificationBatchRelanceRetards;
import fr.rbo.elitbatch.beans.EmpruntBean;
import fr.rbo.elitbatch.beans.OuvrageBean;
import fr.rbo.elitbatch.beans.UserBean;
import fr.rbo.elitbatch.proxies.APIProxy;
import fr.rbo.elitbatch.service.EmailService;
import fr.rbo.elitbatch.service.RelanceRetards;
import jdk.nashorn.internal.ir.annotations.Ignore;
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

    private EmailService emailService;
    @Autowired
    private PlanificationBatchRelanceRetards planificationBatchRelanceRetards;

    @Test
    public void testRien() {

    }

//    @Test
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

//    @Test
    public void testTraitementRelance() {
        relanceRetards.mailsDeRelances();
    }
//    @Test
    public void testPlanification(){
        planificationBatchRelanceRetards.PlanificationBatchRelanceRetardsCron();
    }
}