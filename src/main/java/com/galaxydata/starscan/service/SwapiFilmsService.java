package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Film;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    @Override
    public Film getById(String id, HttpServletRequest request) {
        Film film = super.getById(id, request);
        replaceArrayUrls(film, request);
        return film;
    }

    private void replaceArrayUrls(Film film, HttpServletRequest request) {
        if (film == null || film.getProperties() == null) {
            return;
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String[] characters = film.getProperties().getCharacters();

        if (characters != null) {
            film.getProperties().setCharacters(Arrays.stream(characters)
                    .map(characterUrl -> characterUrl.startsWith(swapiMainUrl) ? characterUrl.replace(swapiMainUrl, baseUrl) : characterUrl)
                    .toArray(String[]::new));
        }
        // -------------------------------
        String[] vehicles = film.getProperties().getVehicles();

        if (vehicles != null) {
            film.getProperties().setVehicles(Arrays.stream(vehicles)
                    .map(vehiclesUrl -> vehiclesUrl.startsWith(swapiMainUrl) ? vehiclesUrl.replace(swapiMainUrl, baseUrl) : vehiclesUrl)
                    .toArray(String[]::new));
        }
        // -------------------------------
        String[] starships = film.getProperties().getStarships();

        if (starships != null) {
            film.getProperties().setStarships(Arrays.stream(starships)
                    .map(starshipsUrl -> starshipsUrl.startsWith(swapiMainUrl) ? starshipsUrl.replace(swapiMainUrl, baseUrl) : starshipsUrl)
                    .toArray(String[]::new));
        }
    }

}
