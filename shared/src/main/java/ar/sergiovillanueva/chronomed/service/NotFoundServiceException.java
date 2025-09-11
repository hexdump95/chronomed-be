package ar.sergiovillanueva.chronomed.service;

public class NotFoundServiceException extends RuntimeException {

    public NotFoundServiceException(String message) {
        super(message);
    }
}
