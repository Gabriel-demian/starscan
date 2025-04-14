package com.galaxydata.starscan.exception;

/**
 * Exception thrown when an invalid SWAPI URL is encountered.
 */
public class InvalidSwapiUrlException extends RuntimeException {

    public InvalidSwapiUrlException(String message) {
        super(message);
    }
    /**
     * Constructs a new `InvalidSwapiUrlException` with the specified message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public InvalidSwapiUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}