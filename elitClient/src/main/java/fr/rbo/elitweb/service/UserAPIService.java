package fr.rbo.elitweb.service;

import fr.rbo.elitweb.beans.RoleBean;
import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitweb.exceptions.APIException;
import fr.rbo.elitweb.exceptions.NotFoundException;
import fr.rbo.elitweb.proxies.APIProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserAPIService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPIService.class);

    @Autowired
    private APIProxy apiProxy;

    public UserBean creerUser (@Valid UserBean user) {
        LOGGER.debug("creerUser email : " + user.getEmail());
        try {
            UserBean userExistant = apiProxy.recupererUnUser(user.getId());
            LOGGER.warn("User déjà existant pour cet id Web");
            return userExistant;
        } catch (NotFoundException e) {
            try {
                UserBean userACreer = new UserBean();
                userACreer.setEmail(user.getEmail());
                userACreer.setName(user.getName());
                userACreer.setLastName(user.getLastName());
                userACreer.setId(user.getId());
                userACreer.setPassword(user.getPassword());
                userACreer.setActive(user.isActive());
                userACreer.setRoles(user.getRoles());
                return apiProxy.creerUnUser(userACreer);
            } catch (RuntimeException ex) {
                throw new APIException("Post User" ,ex.getMessage(),ex.getStackTrace().toString());
            }
        } catch (RuntimeException e) {
            throw new APIException("Post User" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
    public UserBean recupererUnUserParEmail(String email) {
        LOGGER.debug("recupererUnUserParEmail : " + email);
        try {
            return apiProxy.recupererUnUserParEmail(email);
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get user par email" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

    public RoleBean recupererUnRoleParRole(String role) {
        LOGGER.debug("recupererUnRoleParRole : " + role);
        try {
            return apiProxy.roleParRole(role);
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Role par role" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
}
