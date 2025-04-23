package com.fullcycle.admin.catalogo.domain.exceptions;

import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> errors) {
        super(aMessage);
        this.errors = errors;
    }

    public static DomainException with(Error aError) {
        return new DomainException(aError.message(), List.of(aError));
    }

    public static DomainException with(final List<Error> aErrors) {
        return new DomainException("", aErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
