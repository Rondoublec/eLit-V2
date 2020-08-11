package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.beans.UserBean;
import fr.rbo.elitweb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static final String REGISTRATION = "registration";

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Affiche le formulaire de connexion
     * @param model model
     * @return formulaire de connexion
     */
    @GetMapping(path = "/login")
    public ModelAndView login(Model model){
        LOGGER.debug("Get /login");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * Affiche le formulaire de création de compte
     * @return formulaire de création de compte
     */
    @GetMapping(path = "/registration")
    public ModelAndView registration(){
        LOGGER.debug("Get /registration");
        ModelAndView modelAndView = new ModelAndView();
        UserBean userBean = new UserBean();
        modelAndView.addObject("userBean", userBean);
        modelAndView.setViewName(REGISTRATION);
        return modelAndView;
    }

    /**
     * Récupère et traite les information de création de compte
     * @param userBean données de l'utilisateur à créer
     * @param bindingResult resultat des validations
     * @return formulaire de création de compte avec répoonse de l'action
     */
    @PostMapping(path = "/registration")
    public ModelAndView creerNouveauUser(@Valid UserBean userBean, BindingResult bindingResult) {
        LOGGER.debug("Post /registration");
        ModelAndView modelAndView = new ModelAndView();

        if (!bindingResult.hasErrors()) {
            UserBean userExists = userService.findUserByEmail(userBean.getEmail());
            if (userExists != null) {
                bindingResult.rejectValue("email", "error.user","Ce compte existe déjà");
            }
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(REGISTRATION);
        } else {
            userService.saveUser(userBean);
            modelAndView.addObject("successMessage", "Le compte a été créé avec succès");
            modelAndView.addObject("userBean", userBean);
            modelAndView.setViewName(REGISTRATION);
        }
        return modelAndView;
    }

}
