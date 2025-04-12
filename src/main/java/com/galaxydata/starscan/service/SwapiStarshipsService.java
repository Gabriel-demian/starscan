package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Starship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}