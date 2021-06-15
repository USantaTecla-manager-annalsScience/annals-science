package es.upm.annalsscience.domain.exceptions;

public class PersonNotExistException extends DomainException {

    public PersonNotExistException(String message) {
        super(message);
    }
}
