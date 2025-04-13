package com.galaxydata.starscan.controller;

import com.galaxydata.starscan.config.PaginationRequest;
import com.galaxydata.starscan.dto.Person;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.service.SwapiPeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PeopleControllerTest {

    @Mock
    private SwapiPeopleService peopleService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PeopleController peopleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPeople_Success() {
        PaginationRequest paginationRequest = new PaginationRequest(1, 10);
        SwapiListResponse mockResponse = new SwapiListResponse();
        when(peopleService.getList(1, 10, request)).thenReturn(mockResponse);

        ResponseEntity<?> response = peopleController.getPeople(paginationRequest, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(peopleService, times(1)).getList(1, 10, request);
    }

    @Test
    void testGetPeopleById_Success() {
        String personId = "1";
        Person mockPerson = new Person();
        when(peopleService.getById(personId, request)).thenReturn(mockPerson);

        ResponseEntity<?> response = peopleController.getPeopleById(personId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPerson, response.getBody());
        verify(peopleService, times(1)).getById(personId, request);
    }
}
