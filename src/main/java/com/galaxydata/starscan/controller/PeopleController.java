package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiPeopleService;
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
@RequestMapping("/starscan/people")
public class PeopleController {

    private static final Logger logger = LoggerFactory.getLogger(PeopleController.class);
    private final SwapiPeopleService peopleService;

    public PeopleController(SwapiPeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getPeopleById(@PathVariable String id, HttpServletRequest request) {
        try {
            Person person = peopleService.getById(id, request);
            return ResponseEntity.ok(person);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Person not found: {}", ex.getMessage());
            throw new ControllerException("Person not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
