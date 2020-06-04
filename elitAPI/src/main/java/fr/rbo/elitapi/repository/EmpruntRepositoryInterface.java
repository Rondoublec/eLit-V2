package fr.rbo.elitapi.repository;

import fr.rbo.elitapi.entity.Emprunt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpruntRepositoryInterface {

    List<Emprunt> rechercheEmprunt(Emprunt empruntCherche,String action);


}
