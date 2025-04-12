package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.galaxydata.starscan.util.UrlUtil.adaptSwapiPageUrl;
import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;

public abstract class BaseSwapiService<T> {

    protected final RestTemplate restTemplate = new RestTemplate();
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${swapi.main.url}")
    private String swapiMainUrl;

    protected abstract String getPath();

    protected abstract Logger getLogger();

    protected abstract Class<T> getEntityClass();

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

    protected abstract void setEntityUrl(T entity, String url);
}