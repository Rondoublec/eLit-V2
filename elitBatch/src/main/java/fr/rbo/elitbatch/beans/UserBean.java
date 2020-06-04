package fr.rbo.elitbatch.beans;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class UserBean {

    private long id;
    @Email(message = "*eMail invalide")
    @NotEmpty(message = "*eMail obligatoire")
    private String email;
    @Length(min = 5, message = "*Votre mot de passe doit comporter 5 caractères au minimum")
    @NotEmpty(message = "*Mot de passe obligatoire")
    private String password;
    @NotEmpty(message = "*Prénom obligatoire")
    private String name;
    @NotEmpty(message = "*Nom obligatoire")
    private String lastName;
    private boolean active;
    private Set<RoleBean> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RoleBean> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBean> roles) {
        this.roles = roles;
    }

}
