package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.service.SwapiStarshipsService;
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

class StarshipsControllerTest {

    @Mock
    private SwapiStarshipsService starshipsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private StarshipsController starshipsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStarships_Success() {
        SwapiListResponse mockResponse = new SwapiListResponse();
        when(starshipsService.getList(1, 10, request)).thenReturn(mockResponse);

        ResponseEntity<?> response = starshipsController.getStarships(1, 10, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(starshipsService, times(1)).getList(1, 10, request);
    }

    @Test
    void testGetStarshipsById_Success() {
        String starshipId = "1";
        Starship mockStarship = new Starship();
        when(starshipsService.getById(starshipId, request)).thenReturn(mockStarship);

        ResponseEntity<?> response = starshipsController.getStarshipsById(starshipId, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockStarship, response.getBody());
        verify(starshipsService, times(1)).getById(starshipId, request);
    }
}
