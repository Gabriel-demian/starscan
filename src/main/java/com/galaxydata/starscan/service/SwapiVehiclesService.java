package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Vehicle;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SwapiVehiclesService extends BaseSwapiService<Vehicle>{

    private static final Logger logger = LoggerFactory.getLogger(SwapiVehiclesService.class);
    private static final String PATH = "/vehicles";

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class<Vehicle> getEntityClass() {
        return Vehicle.class;
    }

    @Override
    protected void setEntityUrl(Vehicle vehicle, String url) {
        vehicle.getProperties().setUrl(url);
    }

    @Override
    public Vehicle getById(String id, HttpServletRequest request) {
        Vehicle vehicle = super.getById(id, request);
        replaceArrayUrls(vehicle, request,
                v -> v.getProperties().getFilms(),
                (v, films) -> v.getProperties().setFilms(films));
        return vehicle;
    }

    @Override
    public Vehicle getByName(String name, HttpServletRequest request) {
        Vehicle vehicle = super.getByName(name, request);
        replaceArrayUrls(vehicle, request,
                v -> v.getProperties().getFilms(),
                (v, films) -> v.getProperties().setFilms(films));
        return vehicle;
    }

}
