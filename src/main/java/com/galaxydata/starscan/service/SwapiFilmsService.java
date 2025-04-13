package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.galaxydata.starscan.util.UrlUtil.getBaseUrl;

@Service
public class SwapiFilmsService extends BaseSwapiService<Film> {

    private static final Logger logger = LoggerFactory.getLogger(SwapiFilmsService.class);

    @Override
    protected String getPath() {
        return "/films";
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

}
