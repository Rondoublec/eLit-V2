package fr.rbo.elitapi.controller;

import fr.rbo.elitapi.entity.User;
import fr.rbo.elitapi.exceptions.NotFoundException;
import fr.rbo.elitapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    /**
     * renvoi la liste des comptes existants
     * @return liste des comptes existants
     */
    @GetMapping(value="/users")
    public List<User> listeDesUsers(){
        LOGGER.debug("Get /users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new NotFoundException("Aucun utilisateur présent");
        return users;
    }

    /**
     * renvoie les informations du compte correspondant à l'id
     * @param id du user
     * @return user
     */
    @GetMapping(value = "/user/{id}")
    public User recupererUser (@PathVariable("id") Long id){
        LOGGER.debug("Get /user/{id} " + id);
        User user = userRepository.findById(id).orElseThrow(() ->
                        new NotFoundException("Utilisateur inexistant"));
        return user;
    }

    /**
     * renvoie les informations du compte correspondant à l'email
     * @param email du user
     * @return user
     */
    @GetMapping(value = "/user/email/{email}")
    public User recupererUserParEmail (@PathVariable("email") String email){
        LOGGER.debug("/user/email/{email} " + email);
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("Email utilisateur inexistant");
        return user;
    }

    /**
     * crée le compte correspondant aux information contenues dans le flux
     * @param user données
     * @return user
     */
    @PostMapping(value = "/user/")
    @ResponseBody
    public User creerUser (@RequestBody User user){
        LOGGER.debug("Post /user/");
        User newUser = userRepository.findById(user.getId()).orElse(new User());
        majUser(user, newUser);
        userRepository.save(newUser);
        user = newUser;
        return user;
    }

    private void majUser(User userSource, User userCible) {
        userCible.setEmail(userSource.getEmail());
        userCible.setName(userSource.getName());
        userCible.setLastName(userSource.getLastName());
        userCible.setPassword(userSource.getPassword());
        userCible.setRoles(userSource.getRoles());
        userCible.setActive(userSource.isActive());
    }
}
