package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.entity.Role;
import fr.rbo.elitapi.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleRepository roleRepository;

    /**
     * renvoie la liste des roles existants
     * @return liste des roles existants
     */
    @GetMapping(value="/roles")
    public List<Role> listeDesRoles(){
        LOGGER.debug("Get /roles/");
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) throw new NotFoundException("Role non trouvé");
        return roles;
    }

    /**
     * renvoie le role correspondant au role demandé pour vérifier son existance
     * @param role recherché
     * @return role correspondant au role demandé
     */
    @GetMapping(value = "/roles/role/{role}")
    public Role recupererUnRoleParRole (@PathVariable("role") String role){
        LOGGER.debug("Get /roles/role/{role}" + role);
        Role roleTrouve = roleRepository.findByRole(role);
        if (roleTrouve == null) throw new NotFoundException("Role inexistant");
        return roleTrouve;
    }

}
