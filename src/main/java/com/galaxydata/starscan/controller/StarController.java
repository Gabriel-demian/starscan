package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.service.SwapiFilmsService;
import com.galaxydata.starscan.service.SwapiPeopleService;
import com.galaxydata.starscan.service.SwapiStarshipsService;
import com.galaxydata.starscan.service.SwapiVehiclesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class StarController {

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
    public ResponseEntity<?> getPeople(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request) {
        SwapiListResponse people = peopleService.getList(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(people);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<?> getPeopleById(@PathVariable String id, HttpServletRequest request) {
        Person person = peopleService.getById(id, request);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/starships")
    public ResponseEntity<?> getStarships(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request) {
        SwapiListResponse starships = starshipsService.getList(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(starships);
    }

    @GetMapping("/starships/{id}")
    public ResponseEntity<?> getStarshipsById(@PathVariable String id, HttpServletRequest request) {
        Starship starship = starshipsService.getById(id, request);
        return ResponseEntity.ok(starship);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicles(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request){
        SwapiListResponse vehicles = vehiclesService.getList(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<?> getSVehiclesById(@PathVariable String id, HttpServletRequest request) {
        Vehicle vehicle = vehiclesService.getById(id, request);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/films")
    public ResponseEntity<?> getFilms(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request){
        SwapiFilmListResponse films = filmsService.getFilmList(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(films);
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<?> getSFilmsById(@PathVariable String id, HttpServletRequest request) {
        Film film = filmsService.getById(id, request);
        return ResponseEntity.ok(film);
    }

}
