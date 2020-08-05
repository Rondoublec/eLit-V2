package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Reservation;
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
public class ReservationRepositoryImpl implements ReservationRepositoryInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepositoryImpl.class);

    final EntityManager em;
    public ReservationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Reservation> rechercheReservation(Reservation reservationCherche ,String action) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> reservation = cq.from(Reservation.class);
        List<Predicate> predicates = new ArrayList<>();
        LOGGER.debug("rechercheReservation, avec action = " + action);

        try {
            if (!reservationCherche.getUser().getEmail().isEmpty()) {
                predicates.add(cb.equal(reservation.get("user").get("email"), reservationCherche.getUser().getEmail()));
            }
        } catch (NullPointerException e) {}
        try {
            if (!reservationCherche.getOuvrage().getOuvrageTitre().isEmpty()) {
                predicates.add(cb.like(reservation.get("ouvrage").get("ouvrageTitre"), "%" + reservationCherche.getOuvrage().getOuvrageTitre() + "%"));
            }
        } catch (NullPointerException e) {}
        try {
            if (!reservationCherche.getOuvrage().getOuvrageReference().isEmpty()) {
                predicates.add(cb.like(reservation.get("ouvrage").get("ouvrageReference"), "%" + reservationCherche.getOuvrage().getOuvrageReference() + "%"));
            }
        } catch (NullPointerException e) {}

        try {
            if (!reservationCherche.getReservationActive().toString().isEmpty()) {
                predicates.add(cb.equal(reservation.get("reservationActive"), reservationCherche.getReservationActive()));
            }
        } catch (NullPointerException e) {}

        try {
            if (action.equals("active")){
                predicates.add(cb.isTrue(reservation.get("reservationActive")));
            }
        } catch (NullPointerException e) {}
        try {
            if (action.equals("close")){
                predicates.add(cb.isFalse(reservation.get("reservationActive")));
            }
        } catch (NullPointerException e) {}
        try {
            if (action.equals("notif")){
                predicates.add(cb.isNotNull(reservation.get("reservationDateNotif")));
            }
        } catch (NullPointerException e) {}

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(reservation.get("reservationDateDemande")));
        }
        return em.createQuery(cq).getResultList();
    }
}
