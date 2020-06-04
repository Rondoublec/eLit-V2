package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {

}
