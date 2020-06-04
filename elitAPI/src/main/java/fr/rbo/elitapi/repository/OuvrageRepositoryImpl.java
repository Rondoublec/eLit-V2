package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Ouvrage;
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
public class OuvrageRepositoryImpl implements OuvrageRepositoryInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageRepositoryImpl.class);

    final EntityManager em;
    public OuvrageRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Ouvrage> rechercheOuvrage(Ouvrage ouvrageCherche) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ouvrage> cq = cb.createQuery(Ouvrage.class);
        Root<Ouvrage> ouvrage = cq.from(Ouvrage.class);
        List<Predicate> predicates = new ArrayList<>();
        LOGGER.debug("rechercheOuvrage");

        try {
            if (!ouvrageCherche.getBibliotheque().getBibliothequeId().toString().isEmpty()) {
                predicates.add(cb.equal(ouvrage.get("bibliotheque").get("bibliothequeId"), ouvrageCherche.getBibliotheque().getBibliothequeId()));
            }
        } catch (NullPointerException e) {}
        try {
            if (!ouvrageCherche.getOuvrageTitre().isEmpty()) {
                predicates.add(cb.like(ouvrage.get("ouvrageTitre"), "%" + ouvrageCherche.getOuvrageTitre() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!ouvrageCherche.getOuvrageAuteur().isEmpty()) {
                predicates.add(cb.like(ouvrage.get("ouvrageAuteur"), "%" + ouvrageCherche.getOuvrageAuteur() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!ouvrageCherche.getOuvrageStyle().isEmpty()) {
                predicates.add(cb.like(ouvrage.get("ouvrageStyle"), "%" + ouvrageCherche.getOuvrageStyle() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!ouvrageCherche.getOuvrageResume().isEmpty()) {
                predicates.add(cb.like(ouvrage.get("ouvrageResume"), "%" + ouvrageCherche.getOuvrageResume() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!ouvrageCherche.getOuvrageQuantite().isEmpty()) {
                if (ouvrageCherche.getOuvrageQuantite().equals("1")) {
                    predicates.add(cb.greaterThan(ouvrage.get("ouvrageQuantite"), 0));
                } else {
                    predicates.add(cb.equal(ouvrage.get("ouvrageQuantite"), ouvrageCherche.getOuvrageQuantite()));
                }
            }
        } catch (NullPointerException e) {}

        if (!predicates.isEmpty()) cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }
}
