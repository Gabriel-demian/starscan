package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.service.SwapiPeopleService;
import com.galaxydata.starscan.service.SwapiStarshipsService;
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

    public StarController(SwapiPeopleService peopleService, SwapiStarshipsService starshipsService) {
        this.peopleService = peopleService;
        this.starshipsService = starshipsService;
    }

    @GetMapping("/people")
    public ResponseEntity<?> getPeople(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request) {
        SwapiListResponse people = peopleService.getPeople(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(people);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<?> getPeopleById(@PathVariable String id, HttpServletRequest request) {
        Person person = peopleService.getPersonById(id, request);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/starships")
    public ResponseEntity<?> getStarships(
            @Valid PaginationRequest paginationRequest,
            HttpServletRequest request) {
        SwapiListResponse starships = starshipsService.getStarships(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                request
        );
        return ResponseEntity.ok(starships);
    }

    @GetMapping("/starships/{id}")
    public ResponseEntity<?> getStarshipsById(@PathVariable String id, HttpServletRequest request) {
        Starship starship = starshipsService.getStarshipById(id, request);
        return ResponseEntity.ok(starship);
    }

}
