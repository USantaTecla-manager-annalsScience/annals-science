package es.upm.annalsscience.domain.exceptions;

public class UserAlreadyExistException extends DomainException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
