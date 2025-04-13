package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiStarshipsService;
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
@RequestMapping("/starscan/starships")
public class StarshipsController {

    private static final Logger logger = LoggerFactory.getLogger(StarshipsController.class);
    private final SwapiStarshipsService starshipsService;

    public StarshipsController(SwapiStarshipsService starshipsService) {
        this.starshipsService = starshipsService;
    }

    @GetMapping
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

    @GetMapping("/{id}")
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
}