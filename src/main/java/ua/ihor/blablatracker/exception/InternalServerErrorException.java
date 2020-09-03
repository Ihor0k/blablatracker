package ua.ihor.blablatracker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InternalServerErrorException extends ResponseStatusException {
    public InternalServerErrorException(Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", throwable);
        log.warn(throwable.toString());
    }
}
