package com.galaxydata.starscan.util;

import com.galaxydata.starscan.exception.InvalidSwapiUrlException;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {

    /**
     * Adapts a SWAPI pagination URL to the application's base URL.
     *
     * @param swapiUrl The original SWAPI pagination URL.
     * @param request  The HTTP request object for constructing base URLs.
     * @param path     The path to append to the base URL.
     * @return The adapted URL or null if the input URL is null or invalid.
     */
    public static String adaptSwapiPageUrl(String swapiUrl, HttpServletRequest request, String path) {
        if (swapiUrl == null) return null;

        try {
            URI uri = new URI(swapiUrl);
            String query = uri.getQuery(); // page=2&limit=10
            return getBaseUrl(request) + path + "?" + query; // Add "?" before the query
        } catch (URISyntaxException e) {
            throw new InvalidSwapiUrlException("Invalid SWAPI URL: " + swapiUrl, e);
        }
    }

    /**
     * Constructs the base URL of the application from the HTTP request.
     *
     * @param request The HTTP request object.
     * @return The base URL as a string.
     */
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}