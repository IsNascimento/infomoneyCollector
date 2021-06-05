package com.isnascimento.infocollector.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotConnectToGivenUrlException extends RuntimeException {

    public CouldNotConnectToGivenUrlException(final String message) {
        super(message);
    }
}
