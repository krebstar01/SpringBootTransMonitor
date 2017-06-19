package com.testservice.domain.model.exception;

/**
 * Created by justin on 23/02/2017.
 */
public class BaseServiceException extends RuntimeException {


    String endpoint;
    String httpMethod;


    public BaseServiceException(String message){
        super(message);
    }

    public BaseServiceException(String message, String endpoint){
        super(message);
        this.endpoint = endpoint;
    }

    public BaseServiceException(String message, String endpoint, String httpMethod){
        super(message);
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
    }


    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
