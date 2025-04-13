package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import com.galaxydata.starscan.service.SwapiVehiclesService;
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
@RequestMapping("/starscan/vehicles")
public class VehiclesController {

    private static final Logger logger = LoggerFactory.getLogger(VehiclesController.class);
    private final SwapiVehiclesService vehiclesService;

    public VehiclesController(SwapiVehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @GetMapping
    public ResponseEntity<?> getVehicles(@Valid PaginationRequest paginationRequest, HttpServletRequest request) {
        try {
            SwapiListResponse vehicles = vehiclesService.getList(
                    paginationRequest.getPage(),
                    paginationRequest.getLimit(),
                    request
            );
            return ResponseEntity.ok(vehicles);
        } catch (Exception ex) {
            logger.error("Error fetching vehicles: {}", ex.getMessage(), ex);
            throw new ControllerException("Error fetching vehicles", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehiclesById(@PathVariable String id, HttpServletRequest request) {
        try {
            Vehicle vehicle = vehiclesService.getById(id, request);
            return ResponseEntity.ok(vehicle);
        } catch (ResourceNotFoundException ex) {
            logger.warn("Vehicle not found: {}", ex.getMessage());
            throw new ControllerException("Vehicle not found", org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
