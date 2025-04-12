package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SwapiPeopleService extends BaseSwapiService<Person> {

    private static final Logger logger = LoggerFactory.getLogger(SwapiPeopleService.class);

    @Override
    protected String getPath() {
        return "/people";
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    protected void setEntityUrl(Person person, String url) {
        person.getProperties().setUrl(url);
    }
}