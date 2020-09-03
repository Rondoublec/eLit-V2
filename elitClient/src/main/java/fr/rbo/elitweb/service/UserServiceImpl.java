package fr.rbo.elitweb.service;

import fr.rbo.elitweb.beans.RoleBean;
import fr.rbo.elitweb.beans.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserAPIService clientService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder, UserAPIService clientService) {
        this.clientService = clientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserBean findUserByEmail(String email) {
        LOGGER.debug("findUserByEmail : " + email);
        return clientService.recupererUnUserParEmail(email);
    }

    public void saveUser(UserBean user) {
        LOGGER.debug("saveUser email : " + user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        HashSet<RoleBean> roles = new HashSet<>();
        roles.add(clientService.recupererUnRoleParRole("USER"));
        user.setRoles(roles);
        clientService.creerUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.debug("loadUserByUsername : " + userName);
        UserBean user = clientService.recupererUnUserParEmail(userName);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleBean> userRoles) {
        LOGGER.debug("getUserAuthority");
        Set<GrantedAuthority> roles = new HashSet<>();
        for (RoleBean role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(UserBean user, List<GrantedAuthority> authorities) {
        LOGGER.debug("buildUserForAuthentication email : " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
    }

}
