package ar.sergiovillanueva.chronomed.dto;

import java.util.List;

public class KeycloakUserUpdateRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<KeycloakRole> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<KeycloakRole> getRoles() {
        return roles;
    }

    public void setRoles(List<KeycloakRole> roles) {
        this.roles = roles;
    }
}
