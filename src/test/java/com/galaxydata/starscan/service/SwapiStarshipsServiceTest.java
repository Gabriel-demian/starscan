package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.Starship.StarshipProperties;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SwapiStarshipsServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SwapiStarshipsService starshipsService;

    private MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        starshipsService.swapiMainUrl = "https://www.swapi.tech/api";
    }

    @Test
    public void testGetById() {
        Starship starship = new Starship();
        StarshipProperties properties = new StarshipProperties();
        properties.setName("Millennium Falcon");
        properties.setFilms(new String[]{
                "https://www.swapi.tech/api/films/1",
                "https://www.swapi.tech/api/films/2",
                "https://www.swapi.tech/api/films/3"
        }); // Usamos un array de String con 3 elementos
        properties.setUrl("https://www.swapi.tech/api/starships/10");
        starship.setProperties(properties);

        when(objectMapper.convertValue(anyMap(), eq(Starship.class))).thenReturn(starship);

        Starship retrievedStarship = starshipsService.getById("10", request);

        assertNotNull(retrievedStarship);
        assertEquals("Millennium Falcon", retrievedStarship.getProperties().getName());
        assertArrayEquals(new String[]{
                "http://localhost:8080/starscan/films/1",
                "http://localhost:8080/starscan/films/2",
                "http://localhost:8080/starscan/films/3"
        }, retrievedStarship.getProperties().getFilms()); // Usamos assertArrayEquals con 3 elementos
        assertEquals("http://localhost:8080/starscan/starships/10", retrievedStarship.getProperties().getUrl());
    }

    @Test
    public void testGetByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> starshipsService.getById("0", request));
    }

    // Puedes agregar más tests para getList() y otros métodos si es necesario
}