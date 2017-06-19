package com.testservice.domain.model.exception;

/**
 * Created by justin on 23/02/2017.
 */
public class NotFoundException extends BaseServiceException {

    public NotFoundException(String message, String endpoint, String httpMethod){
        super(message);
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
    }
}
