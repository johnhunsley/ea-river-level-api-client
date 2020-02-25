package com.hunsley.ea.client.model;

public class EaApiClientException extends Exception {

    public EaApiClientException() {
    }

    public EaApiClientException(String message) {
        super(message);
    }

    public EaApiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public EaApiClientException(Throwable cause) {
        super(cause);
    }

    public EaApiClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
