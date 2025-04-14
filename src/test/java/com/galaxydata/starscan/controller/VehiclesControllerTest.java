package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.dto.Vehicle;
import com.galaxydata.starscan.service.SwapiVehiclesService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehiclesControllerTest {

    @Mock
    private SwapiVehiclesService vehiclesService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private VehiclesController vehiclesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetVehicles_Success() {
        SwapiListResponse mockResponse = new SwapiListResponse();
        when(vehiclesService.getList(1, 10, request)).thenReturn(mockResponse);

        ResponseEntity<?> response = vehiclesController.getVehicles(1, 10, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(vehiclesService, times(1)).getList(1, 10, request);
    }

    @Test
    void testGetVehiclesById_Success() {
        String vehicleId = "1";
        Vehicle mockVehicle = new Vehicle();
        when(vehiclesService.getById(vehicleId, request)).thenReturn(mockVehicle);

        ResponseEntity<?> response = vehiclesController.getVehiclesById(vehicleId, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockVehicle, response.getBody());
        verify(vehiclesService, times(1)).getById(vehicleId, request);
    }
}
