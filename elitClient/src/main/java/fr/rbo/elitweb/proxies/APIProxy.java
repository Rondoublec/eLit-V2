package fr.rbo.elitweb.proxies;

import feign.Param;
import feign.RequestLine;
import fr.rbo.elitweb.beans.BibliothequeBean;
import fr.rbo.elitweb.beans.EmpruntBean;
import fr.rbo.elitweb.beans.OuvrageBean;
import fr.rbo.elitweb.beans.RoleBean;
import fr.rbo.elitweb.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "elitapi",url = "localhost:8088")
public interface APIProxy {

    @RequestLine("GET /bibliotheques")
    List<BibliothequeBean> findAllBibliotheques();

    @RequestLine("GET /ouvrages")
    List<OuvrageBean> findAll();

    @RequestLine("GET /ouvrage/{id}")
    OuvrageBean findOuvrageById(@Param("id") int id);

    @RequestLine("POST /ouvrages/recherche")
    List<OuvrageBean> rechercheOuvrage(@RequestBody OuvrageBean ouvrage);

    @RequestLine("GET /emprunts")
    List<EmpruntBean> findAllEmprunts();

    @RequestLine("GET /emprunt/{id}")
    EmpruntBean findEmpruntById(@Param("id") int id);

    @RequestLine("POST /emprunts/recherche/criteres")
    List<EmpruntBean> rechercheEmpruntCriteres(@RequestBody EmpruntBean emprunt);
    @RequestLine("POST /emprunts/recherche/rendu")
    List<EmpruntBean> rechercheEmpruntRendu(@RequestBody EmpruntBean emprunt);
    @RequestLine("POST /emprunts/recherche/prolonge")
    List<EmpruntBean> rechercheEmpruntProlonge(@RequestBody EmpruntBean emprunt);
    @RequestLine("POST /emprunts/recherche/relance")
    List<EmpruntBean> rechercheEmpruntRelance(@RequestBody EmpruntBean emprunt);

    @RequestLine("PUT /emprunt/plus/{id}")
    EmpruntBean prolongeEmpruntById(@Param("id") int id);

    @RequestLine("GET /user/{id}")
    UserBean recupererUnUser(@Param("id") Long id);

    @RequestLine("GET /user/email/{email}")
    UserBean recupererUnUserParEmail(@Param("email") String email);

    @RequestLine("POST /user/")
    UserBean creerUnUser(@RequestBody UserBean user);

    @RequestLine("GET /roles/role/{role}")
    RoleBean roleParRole(@Param("role") String role);

}
