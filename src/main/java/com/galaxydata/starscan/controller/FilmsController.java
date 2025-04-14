package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.ErrorResponse;
import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiFilmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/starscan/films")
public class FilmsController {

    private final SwapiFilmsService filmsService;

    public FilmsController(SwapiFilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    @Operation(summary = "Get a list of films", description = "Retrieve a paginated list of Star Wars films.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of films",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SwapiFilmListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
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
            throw new ControllerException("Error fetching films", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a film by ID", description = "Retrieve details of a specific Star Wars film by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the film",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Film.class))),
            @ApiResponse(responseCode = "404", description = "Film not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> getFilmsById(
            @Parameter(description = "ID of the film to retrieve") @PathVariable String id,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            Film film = filmsService.getById(id, request);
            return ResponseEntity.ok(film);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Film not found", org.springframework.http.HttpStatus.NOT_FOUND.value()));
        }
    }

    @PostMapping(params = "title")
    @Operation(summary = "Get Film by title", description = "Retrieve details of Star Wars Film by their title.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the Film",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Film.class))),
            @ApiResponse(responseCode = "404", description = "Film not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> getFilmByTitle(
            @Parameter(description = "Title of the film to retrieve") @RequestParam(value = "title") String title,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {

        try {
            Film film = filmsService.getByTitle(title, request);
            return ResponseEntity.ok(film);
        } catch (ResourceNotFoundException ex) {
            throw new ControllerException("Film not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}