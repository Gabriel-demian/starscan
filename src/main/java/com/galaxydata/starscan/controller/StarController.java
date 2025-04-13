package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiFilmsService;
import com.galaxydata.starscan.service.SwapiPeopleService;
import com.galaxydata.starscan.service.SwapiStarshipsService;
import com.galaxydata.starscan.service.SwapiVehiclesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starscan")
public class StarController {

    private static final Logger logger = LoggerFactory.getLogger(StarController.class);

    private final SwapiPeopleService peopleService;
    private final SwapiStarshipsService starshipsService;
    private final SwapiVehiclesService vehiclesService;
    private final SwapiFilmsService filmsService;

    public StarController(SwapiPeopleService peopleService, SwapiStarshipsService starshipsService, SwapiVehiclesService vehiclesService, SwapiFilmsService filmsService) {
        this.peopleService = peopleService;
        this.starshipsService = starshipsService;
        this.vehiclesService = vehiclesService;
        this.filmsService = filmsService;
    }

    @GetMapping("/people")
    public ResponseEntity<?> getPeople(@Valid PaginationRequest paginationRequest, HttpServletRequest request) {
        try {
            SwapiListResponse people = peopleService.getList(
                    paginationRequest.getPage(),
                    paginationRequest.getLimit(),
                    request
            );
            return ResponseEntity.ok(people);
        } catch (Exception ex) {
            logger.error("Error fetching people: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching people", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<?> getPeopleById(@PathVariable String id, HttpServletRequest request) {
        try {
            Person person = peopleService.getById(id, request);
            return ResponseEntity.ok(person);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Person not found: {}", ex.getMessage());
            throw new ControllerException("Person not found", org.springframework.http.HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error fetching person by ID: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching person by ID", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/starships")
    public ResponseEntity<?> getStarships(@Valid PaginationRequest paginationRequest, HttpServletRequest request) {
        try {
            SwapiListResponse starships = starshipsService.getList(
                    paginationRequest.getPage(),
                    paginationRequest.getLimit(),
                    request
            );
            return ResponseEntity.ok(starships);
        } catch (Exception ex) {
            logger.error("Error fetching starships: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching starships", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/starships/{id}")
    public ResponseEntity<?> getStarshipsById(@PathVariable String id, HttpServletRequest request) {
        try {
            Starship starship = starshipsService.getById(id, request);
            return ResponseEntity.ok(starship);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Starship not found: {}", ex.getMessage());
            throw new ControllerException("Starship not found", org.springframework.http.HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error fetching starship by ID: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching starship by ID", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicles(@Valid PaginationRequest paginationRequest, HttpServletRequest request) {
        try {
            SwapiListResponse vehicles = vehiclesService.getList(
                    paginationRequest.getPage(),
                    paginationRequest.getLimit(),
                    request
            );
            return ResponseEntity.ok(vehicles);
        } catch (Exception ex) {
            logger.error("Error fetching vehicles: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching vehicles", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<?> getVehiclesById(@PathVariable String id, HttpServletRequest request) {
        try {
            Vehicle vehicle = vehiclesService.getById(id, request);
            return ResponseEntity.ok(vehicle);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Vehicle not found: {}", ex.getMessage());
            throw new ControllerException("Vehicle not found", org.springframework.http.HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error fetching vehicle by ID: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching vehicle by ID", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/films")
    public ResponseEntity<?> getFilms(@Valid PaginationRequest paginationRequest, HttpServletRequest request) {
        try {
            SwapiFilmListResponse films = filmsService.getFilmList(
                    paginationRequest.getPage(),
                    paginationRequest.getLimit(),
                    request
            );
            return ResponseEntity.ok(films);
        } catch (Exception ex) {
            logger.error("Error fetching films: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching films", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<?> getFilmsById(@PathVariable String id, HttpServletRequest request) {
        try {
            Film film = filmsService.getById(id, request);
            return ResponseEntity.ok(film);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Film not found: {}", ex.getMessage());
            throw new ControllerException("Film not found", org.springframework.http.HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error fetching film by ID: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching film by ID", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}