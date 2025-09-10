package ar.sergiovillanueva.chronomed.service;

public class NotFoundServiceException extends RuntimeException {

    public NotFoundServiceException() {
        super("Entity not found");
    }
}
