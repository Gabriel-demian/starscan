package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import javax.servlet.http.HttpServletRequest;

import com.galaxydata.starscan.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;

@Service
public class SwapiFilmsService extends BaseSwapiService<Film> {

    private static final Logger logger = LoggerFactory.getLogger(SwapiFilmsService.class);
    private static final String PATH = "/films";

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected void setEntityUrl(Film film, String url) {
        film.getProperties().setUrl(url);
    }

    public SwapiFilmListResponse getFilmList(int page, int limit, HttpServletRequest request) {
        getLogger().info("Fetching film list for path: {} with page: {} and limit: {}", getPath(), page, limit);

        String url = String.format("%s%s?page=%d&limit=%d", swapiMainUrl, getPath(), page, limit);
        SwapiFilmListResponse response = restTemplate.getForObject(url, SwapiFilmListResponse.class);

        if (response != null && response.getResults() != null) {
            String baseUrl = getBaseUrl(request) + getPath();
            response.getResults().forEach(filmResult -> {
                String filmUrl = baseUrl + "/" + filmResult.getUid();
                filmResult.getProperties().setUrl(filmUrl);

                // Update related URLs for starships, vehicles, and characters
                replaceArrayUrls(filmResult, request,
                        f -> f.getProperties().getStarships(),
                        (f, starships) -> f.getProperties().setStarships(starships));

                replaceArrayUrls(filmResult, request,
                        f -> f.getProperties().getVehicles(),
                        (f, vehicles) -> f.getProperties().setVehicles(vehicles));

                replaceArrayUrls(filmResult, request,
                        f -> f.getProperties().getCharacters(),
                        (f, characters) -> f.getProperties().setCharacters(characters));
            });
        }

        return response;
    }

    @Override
    public Film getById(String id, HttpServletRequest request) {
        Film film = super.getById(id, request);

        replaceArrayUrls(film, request,
                f -> f.getProperties().getCharacters(),
                (f, characters) -> f.getProperties().setCharacters(characters));

        replaceArrayUrls(film, request,
                f -> f.getProperties().getVehicles(),
                (f, vehicles) -> f.getProperties().setVehicles(vehicles));

        replaceArrayUrls(film, request,
                f -> f.getProperties().getStarships(),
                (f, starships) -> f.getProperties().setStarships(starships));

        return film;
    }

    public Film getByTitle(String title, HttpServletRequest request) {
        getLogger().info("Fetching film by Title: {} for path: {}", title, getPath());
        String url = String.format("%s%s/?title=%s", swapiMainUrl, getPath(), title);
        Map<String, Object> response = fetchEntityResponse(url);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("result");
        if (results == null || results.isEmpty()) {
            throw new ResourceNotFoundException("Film not found");
        }

        Map<String, Object> firstResult = results.get(0);
        Film film = objectMapper.convertValue(firstResult, getEntityClass());
        String baseUrl = getBaseUrl(request) + getPath();
        setEntityUrl(film, baseUrl + "/" + title);

        return film;
    }

}
