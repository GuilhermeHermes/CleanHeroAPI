package br.com.gubee.interview.core.features.hero.exception;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ObjectNotFoundException(Object search) {
        super("Resource not found. " + search);
    }

}
