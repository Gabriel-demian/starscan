package com.galaxydata.starscan.service;

import com.galaxydata.starscan.dto.Vehicle;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SwapiVehiclesService extends BaseSwapiService<Vehicle>{

    private static final Logger logger = LoggerFactory.getLogger(SwapiVehiclesService.class);

    @Override
    protected String getPath() {
        return "/vehicles";
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
        replaceArrayUrls(vehicle, request);
        return vehicle;
    }

    private void replaceArrayUrls(Vehicle vehicle, HttpServletRequest request) {
        if (vehicle == null || vehicle.getProperties() == null) {
            return;
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String[] films = vehicle.getProperties().getFilms();

        if (films != null) {
            vehicle.getProperties().setFilms(Arrays.stream(films)
                    .map(filmUrl -> filmUrl.startsWith(swapiMainUrl) ? filmUrl.replace(swapiMainUrl, baseUrl) : filmUrl)
                    .toArray(String[]::new));
        }
    }

}
