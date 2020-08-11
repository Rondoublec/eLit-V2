package fr.rbo.elitweb.controller;

import fr.rbo.elitweb.proxies.APIProxy;
import fr.rbo.elitweb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    APIProxy apiProxy;

    @GetMapping(value={"/", "/index"})
    public String index(Model model){
        LOGGER.debug("Get / , /index");
        return "index";
    }

}