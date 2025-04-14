package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiPeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(summary = "Get a list of people", description = "Retrieve a paginated list of Star Wars characters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of people",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SwapiListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getPeople(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            SwapiListResponse people = peopleService.getList(page, limit, request);
            return ResponseEntity.ok(people);
        } catch (Exception ex) {
            logger.error("Error fetching people: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching people", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID", description = "Retrieve details of a specific Star Wars character by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the person",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> getPeopleById(
            @Parameter(description = "ID of the person to retrieve") @PathVariable String id,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            Person person = peopleService.getById(id, request);
            return ResponseEntity.ok(person);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Person not found: {}", ex.getMessage());
            throw new ControllerException("Person not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "name")
    @Operation(summary = "Get people by name", description = "Retrieve details of Star Wars characters by their name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the people",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "404", description = "People not found")
    })
    public ResponseEntity<?> getPeopleByName(
            @Parameter(description = "Name of the person to retrieve") @RequestParam(value = "name") String name,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {

        try {
            Person person = peopleService.getByName(name, request);
            return ResponseEntity.ok(person);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Person not found: {}", ex.getMessage());
            throw new ControllerException("Person not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
