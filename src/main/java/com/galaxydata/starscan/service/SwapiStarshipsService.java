package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Starship;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SwapiStarshipsService extends BaseSwapiService<Starship> {

    private static final Logger logger = LoggerFactory.getLogger(SwapiStarshipsService.class);

    @Override
    protected String getPath() {
        return "/starships";
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class<Starship> getEntityClass() {
        return Starship.class;
    }

    @Override
    protected void setEntityUrl(Starship starship, String url) {
        starship.getProperties().setUrl(url);
    }

    @Override
    public Starship getById(String id, HttpServletRequest request) {
        Starship starship = super.getById(id, request);
        replaceArrayUrls(starship, request);
        return starship;
    }

    private void replaceArrayUrls(Starship starship, HttpServletRequest request) {
        if (starship == null || starship.getProperties() == null) {
            return;
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String[] films = starship.getProperties().getFilms();

        if (films != null) {
            starship.getProperties().setFilms(Arrays.stream(films)
                    .map(filmUrl -> filmUrl.startsWith(swapiMainUrl) ? filmUrl.replace(swapiMainUrl, baseUrl) : filmUrl)
                    .toArray(String[]::new));
        }
    }
}