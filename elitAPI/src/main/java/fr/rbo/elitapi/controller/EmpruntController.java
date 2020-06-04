package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.User;
import fr.rbo.elitapi.exceptions.NotAcceptableException;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.EmpruntRepository;
import fr.rbo.elitapi.repository.EmpruntRepositoryInterface;
import fr.rbo.elitapi.repository.OuvrageRepository;
import fr.rbo.elitapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@RestController
public class EmpruntController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntController.class);

    @Value("${emprunt.duree.initiale}")
    private int empruntDureeInitiale;
    @Value("${emprunt.duree.prolongation}")
    private int empruntDureeProlongation;

    @Autowired
    EmpruntRepository empruntRepository;
    @Autowired
    EmpruntRepositoryInterface empruntRecherche;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OuvrageRepository ouvrageRepository;

    /**
     * renvoie la liste des emprunts
     * @return liste des emprunts
     */
    @GetMapping(value="/emprunts")
    public List<Emprunt> listeDesEmprunts(){
        LOGGER.debug("Get /emprunts");
        List<Emprunt> emprunts = empruntRepository.findAll();
        if (emprunts.isEmpty()) throw new NotFoundException("Il n'y a pas d'emprunt correspondant à votre recherche");
        return emprunts;
    }

    /**
     * renvoie la liste des emprunts répondants aux critères  action et/ou empruntCherche
     * @param action demandée
     * @param empruntCherche critères
     * @return liste des emprunts correspondants au critères
     */
    @PostMapping(value="/emprunts/recherche/{action}")
    public List<Emprunt> listeDesEmpruntsSelonCriteres( @PathVariable("action") String action
            , @RequestBody Emprunt empruntCherche){
        LOGGER.debug("Post /emprunts/recherche/{action} " + action);
        List<Emprunt> emprunts = empruntRecherche.rechercheEmprunt(empruntCherche, action);
        if (emprunts.isEmpty()) throw new NotFoundException("Il n'y a pas d'ouvrage correspondant à votre recherche");
        return emprunts;
    }

    /**
     * renvoie les informations de l'emprunt cotrrespondant à l'id
     * @param id de l'emprunt
     * @return emprunt
     */
    @GetMapping(value = "/emprunt/{id}")
    public Optional<Emprunt> recupererUnEmprunt (@PathVariable("id") Long id){
        LOGGER.debug("Get /emprunt/{id} " + id);
        Optional<Emprunt> emprunt = empruntRepository.findById(id);
        if (!emprunt.isPresent()) throw new NotFoundException("Cet emprunt n'existe pas");
        return emprunt;
    }

    /**
     * prolonge une seule fois l'emprunt en cours (non rendu) correspondant à l'id
     * , de 28 jours supplémentaires à la date de restitution initiale
     * @param id de l'emprunt
     * @return emprunt
     */
    @PutMapping(value = "/emprunt/plus/{id}")
    public Emprunt prolongerEmprunt (@PathVariable("id") Long id){
        LOGGER.debug("Put /emprunt/plus/{id} " + id);
        Emprunt emprunt = empruntRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Emprunt inexistant, non trouvé"));
        if (null != emprunt.getEmpruntDateProlongation()) {
            throw new NotAcceptableException("Prolongation impossible, emprunt déjà prolongé une fois");
        }
        if (null != emprunt.getEmpruntDateRetour()) {
            throw new NotAcceptableException("Prolongation impossible, ouvrage déjà rendu");
        }
        Date dateNow = Calendar.getInstance().getTime();
        Date dateProlongation = dateFinPeriode(emprunt.getEmpruntDateFin(),empruntDureeInitiale);
        if (dateNow.after(dateProlongation)) {
            emprunt.setEmpruntRelance(true);
        }
        emprunt.setEmpruntDateProlongation(dateFinPeriode(emprunt.getEmpruntDateFin(), empruntDureeInitiale));
        emprunt.setEmpruntProlongation(true);
        empruntRepository.save(emprunt);
        return emprunt;
    }

    /**
     * crée un emprunt pour l'utilisateur et l'ouvrage spécifiés dans le flux et met à jour (décremente de 1) la quantité d'ouvrages disponibles
     * @param emprunt de l'emprunt
     * @return emprunt
     */
    @PostMapping(value = "/emprunt/ajout")
    public Emprunt creerEmprunt (@RequestBody Emprunt emprunt){
        LOGGER.debug("Post /emprunt/ajout");
        Ouvrage ouvrage = ouvrageRepository.findById(emprunt.getOuvrage().getOuvrageId()).orElseThrow(() ->
                new NotFoundException("Ouvrage inconnu"));
        if (Integer.parseInt(ouvrage.getOuvrageQuantite() )< 1) {
            throw new NotAcceptableException("Emprunt impossible, ouvrage non disponible");
        }
        User user = userRepository.findById(emprunt.getUser().getId()).orElseThrow(() ->
                new NotFoundException("Utilisateur inconnu"));
        // Vérifs OK, mise à jour de la quantité d'ouvrages restante avant de créer l'emprunt TODO gérer le rollback
        Integer qte = Integer.parseInt(ouvrage.getOuvrageQuantite())-1;
        ouvrage.setOuvrageQuantite(qte.toString());
        ouvrageRepository.save(ouvrage);
        emprunt.setUser(user);
        emprunt.setOuvrage(ouvrage);
        emprunt.setEmpruntDateDebut(Calendar.getInstance().getTime());
        emprunt.setEmpruntDateFin(dateFinPeriode(emprunt.getEmpruntDateDebut(),empruntDureeInitiale));
        emprunt.setEmpruntProlongation(false);
        emprunt.setEmpruntRelance(false);
        emprunt.setEmpruntRendu(false);
        empruntRepository.save(emprunt);
        return emprunt;
    }
    /**
     * crée ou met à jour un emprunt pour l'utilisateur et l'ouvrage spécifiés dans le flux
     * @param emprunt données
     * @return emprunt
     */
    @PostMapping(value = "/emprunt/upsert")
    public Emprunt majOrUpdateEmprunt (@RequestBody Emprunt emprunt){
        LOGGER.debug("Post /emprunt/upsert");
        emprunt.setEmpruntDateDebut(Calendar.getInstance().getTime());
        emprunt.setEmpruntDateFin(dateFinPeriode(emprunt.getEmpruntDateDebut(),empruntDureeInitiale));
        empruntRepository.save(emprunt);
        return emprunt;
    }

    /**
     * met à jour sur l'emprunt la date de restitution et met à jour (incrémente de 1) la quantité d'ouvrages disponibles
     * @param id de l'emprunt
     * @return emprunt
     */
    @GetMapping(value = "/emprunt/retour/{id}")
    public Emprunt retournerEmprunt (@PathVariable("id") Long id){
        LOGGER.debug("Get /emprunt/retour/{id} " + id);
        Emprunt emprunt = empruntRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Emprunt inexistant, non trouvé"));
        if (null != emprunt.getEmpruntDateRetour()) {
            throw new NotAcceptableException("Demande éronnée, ouvrage déjà rendu");
        }
        Ouvrage ouvrage = ouvrageRepository.findById(emprunt.getOuvrage().getOuvrageId()).orElseThrow(() ->
                new NotFoundException("Ouvrage inconnu"));
        // Vérifs OK, mise à jour de la quantité d'ouvrages restante avant de mettre à jour l'emprunt TODO gérer le rollback
        Integer qte = Integer.parseInt(ouvrage.getOuvrageQuantite())+1;
        ouvrage.setOuvrageQuantite(qte.toString());
        ouvrageRepository.save(ouvrage);
        emprunt.setEmpruntDateRetour(Calendar.getInstance().getTime());
        emprunt.setEmpruntRendu(true);
        empruntRepository.save(emprunt);
        return emprunt;
    }

    /**
     * Supprime l'emprunt correspondant à l'id
     * @param id de l'emprunt
     * @return resultat de l'action
     */
    @GetMapping(value = "/emprunt/suppr/{id}")
    public String supprimerEmprunt (@PathVariable("id") Long id){
        LOGGER.debug("Get /emprunt/suppr/{id} " + id);
        // TODO à sécuriser, seulement pour les roles PERSONNEL et ADMIN
        Optional<Emprunt> emprunt = empruntRepository.findById(id);
        if (emprunt.isPresent()) {
            empruntRepository.deleteById(id);
        } else {
            return "Demande éronnée, emprunt inexistant";
        }
        emprunt = empruntRepository.findById(id);
        if (emprunt.isPresent()) {
            return "KO : Suppression echouée";
        } else {
            return "OK : Suppression réussie";
        }
    }

    /**
     * renvoie la liste des emprunts en cours en retards
     * @param id de l'utilisateur
     * @return liste des emprunts en retards pour un utilisateur
     */
    @GetMapping(value="/emprunts/enretard/{id}")
    public List<Emprunt> listeDesEmpruntsEnRetard(@PathVariable("id") long id) {
        LOGGER.debug("Get /emprunt/enretard/{id} " + id);
        User userRecherche = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Utilisateur inexistant"));
        List<Emprunt> emprunts = empruntRepository.findAllByUserAndEmpruntDateFinIsBeforeAndEmpruntDateProlongationIsNullAndEmpruntDateRetourIsNull(userRecherche, new Date());
        emprunts.addAll(empruntRepository.findAllByUserAndEmpruntDateProlongationIsBeforeAndEmpruntDateRetourIsNull(userRecherche, new Date()));
        LOGGER.debug("emprunts " + emprunts.size());
        if (emprunts.isEmpty()) throw new NotFoundException("Aucun emprunt en retard");
        return emprunts;
    }

    private Date dateFinPeriode (Date dateDebut, int duree){
        GregorianCalendar dateFin = new GregorianCalendar();
        dateFin.setTime(dateDebut);
        dateFin.add(GregorianCalendar.DATE,duree);
        return dateFin.getTime();
    }

}
