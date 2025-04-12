package com.galaxydata.starscan.exception;

public class InvalidSwapiUrlException extends RuntimeException {
    public InvalidSwapiUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}