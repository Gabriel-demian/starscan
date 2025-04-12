package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.service.SwapiPeopleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class StarController {

    private final SwapiPeopleService peopleService;

    public StarController(SwapiPeopleService peopleService) {
        this.peopleService = peopleService;
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
}
