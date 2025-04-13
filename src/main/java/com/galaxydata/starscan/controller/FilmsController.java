package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiFilmsService;
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
@RequestMapping("/starscan/films")
public class FilmsController {

    private static final Logger logger = LoggerFactory.getLogger(FilmsController.class);
    private final SwapiFilmsService filmsService;

    public FilmsController(SwapiFilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
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

    @GetMapping("/{id}")
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
