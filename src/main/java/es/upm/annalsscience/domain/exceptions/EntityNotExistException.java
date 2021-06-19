package es.upm.annalsscience.domain.exceptions;

public class EntityNotExistException extends DomainException {
    public EntityNotExistException(String message) {
        super(message);
    }
}
