package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
public class SwapiPeopleService {

    private static final Logger logger = LoggerFactory.getLogger(SwapiPeopleService.class);


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${swapi.main.url}")
    private String swapiMainUrl;
    private final String path = "/people";

    public SwapiListResponse getPeople(int page, int limit, HttpServletRequest request) {

        logger.info("Calling getPeople for this wonderful movie with page: {} and limit: {}", page, limit);

        String url = String.format("%s%s?page=%d&limit=%d", swapiMainUrl, path, page, limit);
        SwapiListResponse response = restTemplate.getForObject(url, SwapiListResponse.class);

        if (response != null && response.getResults() != null) {
            String baseUrl = getBaseUrl(request) + path;
            response.getResults().forEach(p -> p.setUrl(baseUrl + "/" + p.getUid()));
            response.setPrevious(adaptSwapiPageUrl(response.getPrevious(), request));
            response.setNext(adaptSwapiPageUrl(response.getNext(), request));
        }

        return response;
    }



    public Person getPersonById(String id, HttpServletRequest request) {

        logger.info("Calling getPersonById with id: {}", id);

        String url = String.format("%s%s/%s", swapiMainUrl, path, id);

        // Fetch the JSON response
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"ok".equals(response.get("message"))) {
            throw new ResourceNotFoundException("Person not found");
        }

        // Map the "result" to a Person object
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.convertValue(response.get("result"), Person.class);

        logger.info("Created Person with the data: {}", person);

        // Update the URL in the properties using getBaseUrl
        String baseUrl = getBaseUrl(request) + path;
        person.getProperties().setUrl(baseUrl + "/" + person.getUid());

        return person;
    }

    private String adaptSwapiPageUrl(String swapiUrl, HttpServletRequest request) {
        if (swapiUrl == null) return null;

        try {
            URI uri = new URI(swapiUrl);
            String query = uri.getQuery(); // page=2&limit=10
            return getBaseUrl(request) + path + query;
        } catch (Exception e) {
            return null;
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

}
