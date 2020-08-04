package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Emprunt;
import fr.rbo.elitapi.entity.Ouvrage;
import fr.rbo.elitapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    List<Emprunt> findAllByUserAndEmpruntDateFinIsBeforeAndEmpruntDateProlongationIsNullAndEmpruntDateRetourIsNullOrEmpruntDateProlongationIsNotNullAndEmpruntDateProlongationIsBeforeAndEmpruntDateRetourIsNull(User user, Date dateJour, Date dateJourP);
    List<Emprunt> findAllByUserAndEmpruntDateFinIsBeforeAndEmpruntDateProlongationIsNullAndEmpruntDateRetourIsNull(User user, Date dateJour);
    List<Emprunt> findAllByUserAndEmpruntDateProlongationIsBeforeAndEmpruntDateRetourIsNull(User user, Date dateJour);
    List<Emprunt> findAllByOuvrageAndEmpruntRenduFalse(Ouvrage ouvrage);
    Emprunt findByUserAndOuvrageAndEmpruntRenduFalse(User user, Ouvrage ouvrage);


}

