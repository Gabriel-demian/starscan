package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;
import static com.galaxydata.starscan.util.UrlUtil.adaptSwapiPageUrl;

/**
 * Abstract base service for interacting with the SWAPI (Star Wars API).
 * Provides common functionality for fetching lists and individual entities,
 * adapting URLs to the application's base URL, and handling exceptions.
 *
 * @param <T> The type of the entity handled by the service.
 */
public abstract class BaseSwapiService<T> {

    protected final RestTemplate restTemplate = new RestTemplate();
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${swapi.main.url}")
    protected String swapiMainUrl;

    /**
     * Returns the specific path for the SWAPI resource handled by the service.
     *
     * @return The path as a string (e.g., "/films", "/starships").
     */
    protected abstract String getPath();

    /**
     * Returns the logger instance for the service.
     *
     * @return The logger instance.
     */
    protected abstract Logger getLogger();

    /**
     * Returns the class type of the entity handled by the service.
     *
     * @return The class type of the entity.
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Fetches a paginated list of entities from the SWAPI service, adapts their URLs to the application's base URL,
     * and returns the list response.
     *
     * <p>The response includes pagination information, such as `previous` and `next` URLs, which are also adapted
     * to the application's base URL.</p>
     *
     * @param page    The page number to fetch.
     * @param limit   The number of items per page.
     * @param request The HTTP request object used to construct the base URL.
     * @return A `SwapiListResponse` containing the list of entities and pagination information.
     */
    public SwapiListResponse getList(int page, int limit, HttpServletRequest request) {
        getLogger().info("Fetching list for path: {} with page: {} and limit: {}", getPath(), page, limit);

        String url = String.format("%s%s?page=%d&limit=%d", swapiMainUrl, getPath(), page, limit);
        SwapiListResponse response = restTemplate.getForObject(url, SwapiListResponse.class);

        if (response != null && response.getResults() != null) {
            String baseUrl = getBaseUrl(request) + getPath();
            response.getResults().forEach(p -> p.setUrl(baseUrl + "/" + p.getUid()));
            response.setPrevious(adaptSwapiPageUrl(response.getPrevious(), request, getPath()));
            response.setNext(adaptSwapiPageUrl(response.getNext(), request, getPath()));
        }

        return response;
    }

    /**
     * Retrieves an entity by its ID from the SWAPI service, adapts its URLs to the application's base URL,
     * and returns the entity.
     *
     * @param id      The ID of the entity to retrieve.
     * @param request The HTTP request object used to construct the base URL.
     * @return The entity with its URLs adapted to the application's base URL.
     * @throws ResourceNotFoundException if the entity is not found in the SWAPI service.
     */
    public T getById(String id, HttpServletRequest request) {
        getLogger().info("Fetching entity by ID: {} for path: {}", id, getPath());

        String url = String.format("%s%s/%s", swapiMainUrl, getPath(), id);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"ok".equals(response.get("message"))) {
            throw new ResourceNotFoundException("Entity not found");
        }

        T entity = objectMapper.convertValue(response.get("result"), getEntityClass());
        String baseUrl = getBaseUrl(request) + getPath();
        setEntityUrl(entity, baseUrl + "/" + id);

        return entity;
    }

    /**
     * Sets the URL of the given entity.
     *
     * @param entity The entity whose URL needs to be set.
     * @param url    The URL to set for the entity.
     */
    protected abstract void setEntityUrl(T entity, String url);
}