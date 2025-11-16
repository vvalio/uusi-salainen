package dev.valiov.web.salainen.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Base class for all services.
 */
abstract class AbstractService {
    protected <T> ResponseEntity<T> ok(final T entity) {
        return ResponseEntity.ok(entity);
    }

    protected ResponseStatusException notFound(final String message, final Object... args) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(message, args));
    }

    protected ResponseStatusException badRequest(final String message, final Object... args) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(message, args));
    }

    protected ResponseStatusException conflict(final String message, final Object... args) {
        return new ResponseStatusException(HttpStatus.CONFLICT, String.format(message, args));
    }
}
