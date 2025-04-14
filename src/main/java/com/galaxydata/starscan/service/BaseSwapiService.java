package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.galaxydata.starscan.util.UrlUtil.adaptSwapiPageUrl;
import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;

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
        Map<String, Object> response = fetchEntityResponse(url);

        T entity = objectMapper.convertValue(response.get("result"), getEntityClass());
        String baseUrl = getBaseUrl(request) + "/starscan" + getPath();
        setEntityUrl(entity, baseUrl + "/" + id);

        return entity;
    }

    /**
     * Retrieves an entity by its name from the SWAPI service, adapts its URLs to the application's base URL,
     * and returns the entity.
     *
     * <p>The method sends a GET request to the SWAPI service with the provided name as a query parameter.
     * The response is expected to contain a list of results, from which the first result is mapped to the entity class.
     * If no results are found, a {@link ResourceNotFoundException} is thrown.</p>
     *
     * @param name    The name of the entity to retrieve.
     * @param request The HTTP request object used to construct the base URL.
     * @return The entity with its URLs adapted to the application's base URL.
     * @throws ResourceNotFoundException if no entity is found with the given name.
     */
    public T getByName(String name, HttpServletRequest request) {
        getLogger().info("Fetching entity by Name: {} for path: {}", name, getPath());
        String url = String.format("%s%s/?name=%s", swapiMainUrl, getPath(), name);
        Map<String, Object> response = fetchEntityResponse(url);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("result");
        if (results == null || results.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }

        Map<String, Object> firstResult = results.get(0);
        T entity = objectMapper.convertValue(firstResult, getEntityClass());
        String baseUrl = getBaseUrl(request) + "/starscan" + getPath();
        setEntityUrl(entity, baseUrl + "/" + name);

        return entity;
    }

    /**
     * Sets the URL of the given entity.
     *
     * @param entity The entity whose URL needs to be set.
     * @param url    The URL to set for the entity.
     */
    protected abstract void setEntityUrl(T entity, String url);

    /**
     * Replaces the URLs in the entity's array properties with the application's base URL.
     *
     * @param entity   The entity whose URLs need to be replaced.
     * @param request  The HTTP request object used to construct the base URL.
     * @param getFilms A function to get the array of film URLs from the entity.
     * @param setFilms A consumer to set the modified array of film URLs back to the entity.
     * @param <T>      The type of the entity.
     */
    protected <T> void replaceArrayUrls(T entity, HttpServletRequest request, Function<T, String[]> getFilms, BiConsumer<T, String[]> setFilms) {
        if (entity == null) {
            return;
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/starscan";
        String[] films = getFilms.apply(entity);

        if (films != null) {
            setFilms.accept(entity, Arrays.stream(films)
                    .map(filmUrl -> filmUrl.startsWith(swapiMainUrl) ? filmUrl.replace(swapiMainUrl, baseUrl) : filmUrl)
                    .toArray(String[]::new));
        }
    }

    /**
     * Fetches the response for an entity from the SWAPI service and validates it.
     *
     * @param url The URL to fetch the entity from.
     * @return A map containing the response data.
     * @throws ResourceNotFoundException if the response is null or invalid.
     */
    Map<String, Object> fetchEntityResponse(String url) {
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Entity not found", e);
        }
    }

}