package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Starship;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SwapiStarshipsService extends BaseSwapiService<Starship> {

    private static final Logger logger = LoggerFactory.getLogger(SwapiStarshipsService.class);
    private static final String PATH = "/starships";

    @Override
    protected String getPath() {
        return PATH;
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
        replaceArrayUrls(starship, request,
                s -> s.getProperties().getFilms(),
                (s, films) -> s.getProperties().setFilms(films));
        return starship;
    }

}