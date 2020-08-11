package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.Reservation;
import fr.rbo.elitapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByOuvrageAndReservationActiveTrue(Ouvrage ouvrage);
    List<Reservation> findAllByOuvrageAndReservationActiveTrueOrderByReservationDateDemandeAsc(Ouvrage ouvrage);
    List<Reservation> findAllByOuvrageAndReservationActiveTrueAndNotifierFalseAndReservationDateNotifIsNullOrderByReservationDateDemandeAsc(Ouvrage ouvrage);
    List<Reservation> findAllByUserAndReservationActiveTrue(User user);
    List<Reservation> findAllByOuvrageAndUserAndReservationActiveTrue(Ouvrage ouvrage, User user);
    List<Reservation> findAllByNotifierTrueAndReservationActiveTrueAndReservationDateNotifIsNull();
    List<Reservation> findAllByReservationActiveTrueAndReservationDateNotifIsNotNull();
    Reservation findByUserAndOuvrageAndReservationActiveTrue(User user, Ouvrage ouvrage);

}
