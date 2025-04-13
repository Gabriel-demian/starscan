package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Film;
import com.galaxydata.starscan.dto.SwapiFilmListResponse;
import com.galaxydata.starscan.exception.ControllerException;
import com.galaxydata.starscan.service.SwapiFilmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


class FilmsControllerTest {

    @Mock
    private SwapiFilmsService filmsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private FilmsController filmsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilms_Success() {
        PaginationRequest paginationRequest = new PaginationRequest(1, 10);
        SwapiFilmListResponse mockResponse = new SwapiFilmListResponse();
        when(filmsService.getFilmList(1, 10, request)).thenReturn(mockResponse);

        ResponseEntity<?> response = filmsController.getFilms(paginationRequest, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(filmsService, times(1)).getFilmList(1, 10, request);
    }

    @Test
    void testGetFilms_Exception() {
        PaginationRequest paginationRequest = new PaginationRequest(1, 10);
        when(filmsService.getFilmList(1, 10, request)).thenThrow(new RuntimeException("Service error"));

        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmsController.getFilms(paginationRequest, request);
        });

        assertEquals("Error fetching films", exception.getMessage());
        verify(filmsService, times(1)).getFilmList(1, 10, request);
    }

    @Test
    void testGetFilmsById_Success() {
        String filmId = "1";
        Film mockFilm = new Film();
        when(filmsService.getById(filmId, request)).thenReturn(mockFilm);

        ResponseEntity<?> response = filmsController.getFilmsById(filmId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockFilm, response.getBody());
        verify(filmsService, times(1)).getById(filmId, request);
    }

    @Test
    void testGetFilmsById_NotFound() {
        String filmId = "0";
        when(filmsService.getById(filmId, request)).thenThrow(new ControllerException("Film not found", org.springframework.http.HttpStatus.NOT_FOUND));

        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmsController.getFilmsById(filmId, request);
        });

        assertEquals("Film not found", exception.getMessage());
        verify(filmsService, times(1)).getById(filmId, request);
    }
}