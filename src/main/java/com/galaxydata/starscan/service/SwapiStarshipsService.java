package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.galaxydata.starscan.util.UrlUtil.adaptSwapiPageUrl;
import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;

@Service
public class SwapiStarshipsService {

    private static final Logger logger = LoggerFactory.getLogger(SwapiStarshipsService.class);


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${swapi.main.url}")
    private String swapiMainUrl;
    private final String path = "/starships";

    public SwapiListResponse getStarships(int page, int limit, HttpServletRequest request){

        logger.info("Calling getStarships for this wonderful movie with page: {} and limit: {}", page, limit);

        String url = String.format("%s%s?page=%d&limit=%d", swapiMainUrl, path, page, limit);
        SwapiListResponse response = restTemplate.getForObject(url, SwapiListResponse.class);

        if (response != null && response.getResults() != null) {
            String baseUrl = getBaseUrl(request) + path;
            response.getResults().forEach(p -> p.setUrl(baseUrl + "/" + p.getUid()));
            response.setPrevious(adaptSwapiPageUrl(response.getPrevious(), request, path));
            response.setNext(adaptSwapiPageUrl(response.getNext(), request, path));
        }

        return response;
    }

    public Starship getStarshipById (String id, HttpServletRequest request){
        logger.info("Calling getStarshipById with id: {}", id);

        String url = String.format("%s%s/%s", swapiMainUrl, path, id);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"ok".equals(response.get("message"))) {
            throw new ResourceNotFoundException("Starship not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Starship starship = objectMapper.convertValue(response.get("result"), Starship.class);

        logger.info("Created Starship with the data: {}", starship);

        String baseUrl = getBaseUrl(request) + path;
        starship.getProperties().setUrl(baseUrl + "/" + starship.getUid());

        return starship;
    }

}
