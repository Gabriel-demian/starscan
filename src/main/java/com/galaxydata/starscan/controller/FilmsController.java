package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiFilmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/starscan/films")
public class FilmsController {

    private static final Logger logger = LoggerFactory.getLogger(FilmsController.class);
    private final SwapiFilmsService filmsService;

    public FilmsController(SwapiFilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    @Operation(summary = "Get a list of films", description = "Retrieve a paginated list of Star Wars films.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of films"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getFilms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            SwapiFilmListResponse films = filmsService.getFilmList(
                    page,
                    limit,
                    request
            );
            return ResponseEntity.ok(films);
        } catch (Exception ex) {
            logger.error("Error fetching films: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching films", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a film by ID", description = "Retrieve details of a specific Star Wars film by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the film"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<?> getFilmsById(
            @Parameter(description = "ID of the film to retrieve") @PathVariable String id,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            Film film = filmsService.getById(id, request);
            return ResponseEntity.ok(film);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Film not found: {}", ex.getMessage());
            throw new ControllerException("Film not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}