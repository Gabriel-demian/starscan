package com.galaxydata.starscan.util;

import com.galaxydata.starscan.exception.InvalidSwapiUrlException;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class for handling and adapting URLs in the application.
 * Provides methods to construct base URLs and adapt SWAPI pagination URLs
 * to the application's base URL.
 *
 * <p>This class is designed to facilitate seamless integration between the SWAPI service
 * and the application's URL structure by dynamically adapting URLs.</p>
 */
public class UrlUtil {

    /**
     * Adapts a SWAPI pagination URL to the application's base URL.
     *
     * <p>This method takes a SWAPI pagination URL, extracts its query parameters,
     * and constructs a new URL using the application's base URL and the provided path.</p>
     *
     * @param swapiUrl The original SWAPI pagination URL. Can be null.
     * @param request  The HTTP request object used to construct the base URL.
     * @param path     The path to append to the base URL (e.g., "/people", "/starships").
     * @return The adapted URL as a string, or null if the input URL is null.
     * @throws InvalidSwapiUrlException if the provided SWAPI URL is invalid or cannot be parsed.
     */
    public static String adaptSwapiPageUrl(String swapiUrl, HttpServletRequest request, String path) {
        if (swapiUrl == null) return null;

        try {
            URI uri = new URI(swapiUrl);
            if (!uri.isAbsolute()) {
                throw new InvalidSwapiUrlException("Invalid SWAPI URL: " + swapiUrl);
            }
            String query = uri.getQuery(); // Extract query parameters (e.g., page=2&limit=10)
            return getBaseUrl(request) + path + "?" + query; // Construct the adapted URL
        } catch (URISyntaxException e) {
            throw new InvalidSwapiUrlException("Invalid SWAPI URL: " + swapiUrl, e);
        }
    }

    /**
     * Constructs the base URL of the application from the HTTP request.
     *
     * <p>The base URL is constructed using the scheme (http/https), server name, and server port
     * from the provided HTTP request object.</p>
     *
     * @param request The HTTP request object used to extract the base URL components.
     * @return The base URL as a string (e.g., "<a href="http://localhost:8080">...</a>").
     */
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

}