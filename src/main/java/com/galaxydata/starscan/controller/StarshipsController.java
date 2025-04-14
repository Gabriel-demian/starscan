package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiStarshipsService;
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
@RequestMapping("/starscan/starships")
public class StarshipsController {

    private static final Logger logger = LoggerFactory.getLogger(StarshipsController.class);
    private final SwapiStarshipsService starshipsService;

    public StarshipsController(SwapiStarshipsService starshipsService) {
        this.starshipsService = starshipsService;
    }

    @GetMapping
    @Operation(summary = "Get a list of starships", description = "Retrieve a paginated list of Star Wars starships.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of starships",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SwapiListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getStarships(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            SwapiListResponse starships = starshipsService.getList(
                    page,
                    limit,
                    request
            );
            return ResponseEntity.ok(starships);
        } catch (Exception ex) {
            logger.error("Error fetching starships: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching starships", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a starship by ID", description = "Retrieve details of a specific Star Wars starship by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the starship",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Starship.class))),
            @ApiResponse(responseCode = "404", description = "Starship not found")
    })
    public ResponseEntity<?> getStarshipsById(
            @Parameter(description = "ID of the starship to retrieve") @PathVariable String id,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            Starship starship = starshipsService.getById(id, request);
            return ResponseEntity.ok(starship);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Starship not found: {}", ex.getMessage());
            throw new ControllerException("Starship not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}