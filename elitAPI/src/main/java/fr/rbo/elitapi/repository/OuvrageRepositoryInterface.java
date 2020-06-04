package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Ouvrage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OuvrageRepositoryInterface {

    List<Ouvrage> rechercheOuvrage(Ouvrage ouvrageCherche);

}
