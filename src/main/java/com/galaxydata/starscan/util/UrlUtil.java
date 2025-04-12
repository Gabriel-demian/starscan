package com.galaxydata.starscan.util;

import com.galaxydata.starscan.exception.InvalidSwapiUrlException;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.lang.reflect.Field;

/**
 * Utility class for handling and adapting URLs in the application.
 * Provides methods to construct base URLs, adapt SWAPI pagination URLs,
 * and dynamically process objects to replace SWAPI URLs with application-specific URLs.
 *
 * <p>This class uses reflection to inspect and modify object fields at runtime,
 * enabling dynamic URL adaptation for nested objects and arrays.</p>
 */
public class UrlUtil {

    /**
     * Adapts a SWAPI pagination URL to the application's base URL.
     *
     * @param swapiUrl The original SWAPI pagination URL.
     * @param request  The HTTP request object for constructing base URLs.
     * @param path     The path to append to the base URL.
     * @return The adapted URL or null if the input URL is null or invalid.
     * @throws InvalidSwapiUrlException if the provided SWAPI URL is invalid.
     */
    public static String adaptSwapiPageUrl(String swapiUrl, HttpServletRequest request, String path) {
        if (swapiUrl == null) return null;

        try {
            URI uri = new URI(swapiUrl);
            String query = uri.getQuery(); // Extract query parameters (e.g., page=2&limit=10)
            return getBaseUrl(request) + path + "?" + query; // Construct the adapted URL
        } catch (URISyntaxException e) {
            throw new InvalidSwapiUrlException("Invalid SWAPI URL: " + swapiUrl, e);
        }
    }

    /**
     * Constructs the base URL of the application from the HTTP request.
     *
     * @param request The HTTP request object.
     * @return The base URL as a string (e.g., <a href="http://localhost:8080">...</a>).
     */
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * Adapts all `String[]` fields in the given entity by replacing SWAPI URLs with the application's base URL.
     * This method uses reflection to dynamically find and process all `String[]` fields in the entity.
     * It also handles nested objects recursively.
     *
     * @param entity  The object whose `String[]` fields need to be adapted. Must not be null.
     * @param request The HTTP request object used to construct the base URL.
     */
    public static void adaptUrls(Object entity, HttpServletRequest request) {
        if (entity == null) return;

        String baseUrl = getBaseUrl(request);

        // Recursively process the entity
        processObject(entity, baseUrl);
    }

    /**
     * Recursively processes an object to adapt all `String[]` fields by replacing SWAPI URLs with the application's base URL.
     *
     * @param obj     The object to process.
     * @param baseUrl The base URL to use for adapting the URLs.
     */
    private static void processObject(Object obj, String baseUrl) {
        if (obj == null) return;

        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true); // Allow access to private fields
                try {
                    Object value = field.get(obj);

                    // Skip fields from core Java classes
                    if (field.getType().getName().startsWith("java.")) {
                        continue;
                    }

                    if (value instanceof String[]) {
                        // Adapt URLs in String[] fields
                        String[] urls = (String[]) value;
                        for (int i = 0; i < urls.length; i++) {
                            String swapiUrl = urls[i];
                            if (swapiUrl != null) {
                                String id = swapiUrl.substring(swapiUrl.lastIndexOf("/") + 1);
                                String path = field.getName(); // Use the field name as the path
                                urls[i] = baseUrl + "/" + path + "/" + id;
                            }
                        }
                    } else if (value != null && !(value instanceof String)) {
                        // Recursively process nested objects
                        processObject(value, baseUrl);
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Failed to adapt URLs for field: " + field.getName());
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass(); // Move to the superclass
        }
    }
}