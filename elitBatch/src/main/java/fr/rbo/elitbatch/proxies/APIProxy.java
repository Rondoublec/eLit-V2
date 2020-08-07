package fr.rbo.elitbatch.proxies;

import feign.Param;
import feign.RequestLine;
import fr.rbo.elitbatch.beans.BibliothequeBean;
import fr.rbo.elitbatch.beans.EmpruntBean;
import fr.rbo.elitbatch.beans.OuvrageBean;
import fr.rbo.elitbatch.beans.ReservationBean;
import fr.rbo.elitbatch.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @RequestLine("GET /emprunts/enretard/{id}")
    List<EmpruntBean> listeDesEmpruntsEnRetard(@Param("id") long id);

    @RequestLine("GET /reservations/notifiees")
    List<ReservationBean> listeDesReservationsaNotifiees();
    @RequestLine("GET /reservations/anotifier")
    List<ReservationBean> listeDesReservationsaNotifier();
    @RequestLine("PUT /reservation/anotifier/{id}")
    ReservationBean majReservationaNotifierById(@Param("id") long id);
    @RequestLine("PUT /reservation/notification/{id}")
    ReservationBean majDateNotificationById(@Param("id") long id);
    @RequestLine("PUT /reservation/inverseEtat/{id}")
    ReservationBean switchEtatReservationById(@Param("id") long id);

    @RequestLine("GET /users")
    List<UserBean> listeDesUsers();

}
