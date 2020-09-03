package ua.ihor.blablatracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {
    public EntityNotFoundException(String entityName, long id) {
        super(HttpStatus.NOT_FOUND, entityName + " with id " + id + " not found");
    }

    public EntityNotFoundException(String entityName, String id) {
        super(HttpStatus.NOT_FOUND, entityName + " with id " + id + " not found");
    }

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
