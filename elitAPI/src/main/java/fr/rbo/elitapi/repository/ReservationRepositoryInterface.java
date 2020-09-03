package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepositoryInterface {

    List<Reservation> rechercheReservation(Reservation reservationCherche, String action);

}
