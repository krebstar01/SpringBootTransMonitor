package com.testservice.domain.model.exception;

/**
 * Created by justin on 23/02/2017.
 */
public class BadArgumentException extends BaseServiceException {

    public BadArgumentException(String message, String endpoint, String httpMethod){
        super(message);
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
    }


}
