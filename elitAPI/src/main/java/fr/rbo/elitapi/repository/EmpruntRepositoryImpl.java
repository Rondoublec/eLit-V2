package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Emprunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpruntRepositoryImpl implements EmpruntRepositoryInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntRepositoryImpl.class);

    final EntityManager em;
    public EmpruntRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Emprunt> rechercheEmprunt(Emprunt empruntCherche ,String action) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Emprunt> cq = cb.createQuery(Emprunt.class);
        Root<Emprunt> emprunt = cq.from(Emprunt.class);
        List<Predicate> predicates = new ArrayList<>();
        LOGGER.debug("rechercheEmprunt, avec action = " + action);

        try {
            if (!empruntCherche.getUser().getEmail().isEmpty()) {
                predicates.add(cb.equal(emprunt.get("user").get("email"), empruntCherche.getUser().getEmail()));
            }
        } catch (NullPointerException e) {}
        try {
            if (!empruntCherche.getOuvrage().getOuvrageTitre().isEmpty()) {
                predicates.add(cb.like(emprunt.get("ouvrage").get("ouvrageTitre"), "%" + empruntCherche.getOuvrage().getOuvrageTitre() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!empruntCherche.getOuvrage().getOuvrageReference().isEmpty()) {
                predicates.add(cb.like(emprunt.get("ouvrage").get("ouvrageReference"), "%" + empruntCherche.getOuvrage().getOuvrageReference() + "%"));
            }
        } catch (NullPointerException e) {}

        try {
            if (!empruntCherche.getEmpruntProlongation().toString().isEmpty()) {
                predicates.add(cb.equal(emprunt.get("empruntProlongation"), empruntCherche.getEmpruntProlongation()));
            }
        } catch (NullPointerException e) {}
        try {
            if (!empruntCherche.getEmpruntRendu().toString().isEmpty()) {
                predicates.add(cb.equal(emprunt.get("empruntRendu"), empruntCherche.getEmpruntRendu()));
            }
        } catch (NullPointerException e) {}
        try {
            if (!empruntCherche.getEmpruntRelance().toString().isEmpty()) {
                predicates.add(cb.equal(emprunt.get("empruntRelance"), empruntCherche.getEmpruntRelance()));
            }
        } catch (NullPointerException e) {}

        try {
            if (action.equals("rendu")){
                predicates.add(cb.isNotNull(emprunt.get("empruntDateRetour")));
            }
        } catch (NullPointerException e) {}
        try {
            if (action.equals("prolonge")){
                predicates.add(cb.isNotNull(emprunt.get("empruntDateProlongation")));
            }
        } catch (NullPointerException e) {}
        try {
            if (action.equals("relance")){
                predicates.add(cb.isNotNull(emprunt.get("empruntDateRelance")));
            }
        } catch (NullPointerException e) {}

        if (!predicates.isEmpty()) cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }
}
