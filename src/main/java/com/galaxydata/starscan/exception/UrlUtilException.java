package com.galaxydata.starscan.exception;

/**
 * Exception thrown for errors encountered in the `UrlUtil` utility class.
 */
public class UrlUtilException extends RuntimeException {

    /**
     * Constructs a new `UrlUtilException` with the specified message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public UrlUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new `UrlUtilException` with the specified message.
     *
     * @param message The detail message.
     */
    public UrlUtilException(String message) {
        super(message);
    }
}
