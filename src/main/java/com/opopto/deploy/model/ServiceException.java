package com.opopto.deploy.model;

public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
