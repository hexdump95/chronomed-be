package ar.sergiovillanueva.chronomed.service;

public class KeycloakServiceException extends RuntimeException {

    public KeycloakServiceException() {
        super("Keycloak error");
    }
}
