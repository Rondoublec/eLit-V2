package fr.rbo.elitweb.utils;

import fr.rbo.elitweb.beans.EnCoursBean;
import fr.rbo.elitweb.beans.ReservationBean;
import fr.rbo.elitweb.exceptions.NotFoundException;
import fr.rbo.elitweb.proxies.APIProxy;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UtilityClass
public class TransversalUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransversalUtils.class);

    @Autowired
    APIProxy apiProxy;

    public EnCoursBean recupInfosEnCours (int ouvrageId) {
        EnCoursBean enCours = null;
        try {
            enCours = apiProxy.etatEmpruntEnCours(ouvrageId);
        } catch (NotFoundException e) {}
        return enCours;
    }

    public Integer recupNbResa (int ouvrageId) {
        int nbResa = 0;
        List<ReservationBean> reservationBean;
        try {
            reservationBean = apiProxy.listeDesReservationsDeLOuvrage(ouvrageId);
            nbResa = reservationBean.size();
        } catch (NotFoundException e) {}
        return nbResa;
    }

}
