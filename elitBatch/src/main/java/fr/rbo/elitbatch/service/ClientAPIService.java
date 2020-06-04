package fr.rbo.elitbatch.service;

import fr.rbo.elitbatch.beans.UserBean;
import fr.rbo.elitbatch.exceptions.APIException;
import fr.rbo.elitbatch.exceptions.NotFoundException;
import fr.rbo.elitbatch.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientAPIService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAPIService.class);

    @Autowired
    private APIProxy apiProxy;

    /**
     * Recupère la liste des adhérents des bibiliothèques
     * @return liste des utilisateurs
     */
    public List<UserBean> listeUser (){
        LOGGER.debug("listeUser");
        try {
            return apiProxy.listeDesUsers();
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Liste des usagers" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
}
