package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiVehiclesService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/starscan/vehicles")
public class VehiclesController {

    private static final Logger logger = LoggerFactory.getLogger(VehiclesController.class);
    private final SwapiVehiclesService vehiclesService;

    public VehiclesController(SwapiVehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @GetMapping
    @Operation(summary = "Get a list of vehicles", description = "Retrieve a paginated list of Star Wars vehicles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of vehicles",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SwapiListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getVehicles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            SwapiListResponse vehicles = vehiclesService.getList(
                    page,
                    limit,
                    request
            );
            return ResponseEntity.ok(vehicles);
        } catch (Exception ex) {
            logger.error("Error fetching vehicles: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching vehicles", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a vehicle by ID", description = "Retrieve details of a specific Star Wars vehicle by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of vehicles",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })

    public ResponseEntity<?> getVehiclesById(
            @Parameter(description = "ID of the vehicle to retrieve") @PathVariable String id,
            @Parameter(description = "HTTP request object") HttpServletRequest request) {
        try {
            Vehicle vehicle = vehiclesService.getById(id, request);
            return ResponseEntity.ok(vehicle);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Vehicle not found: {}", ex.getMessage());
            throw new ControllerException("Vehicle not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}