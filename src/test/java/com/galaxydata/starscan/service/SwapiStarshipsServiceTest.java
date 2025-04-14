package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.Starship;
import com.galaxydata.starscan.dto.Starship.StarshipProperties;
import com.galaxydata.starscan.dto.SwapiListResponse;
import com.galaxydata.starscan.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testGetList() {
        SwapiListResponse mockResponse = new SwapiListResponse();
        SwapiListResponse.Results result = new SwapiListResponse.Results();
        result.setUid("1");
        mockResponse.setResults(Collections.singletonList(result));
        mockResponse.setPrevious("https://www.swapi.tech/starships/?page=1&limit=10");
        mockResponse.setNext("https://www.swapi.tech/starships/?page=3&limit=10");

        SwapiListResponse response = starshipsService.getList(2, 10, request);

        assertNotNull(response);
        assertEquals(10, response.getResults().size());
        assertEquals("http://localhost:8080/starscan/starships/21", response.getResults().get(0).getUrl());
        assertEquals("http://localhost:8080/starscan/starships?page=1&limit=10", response.getPrevious());
        assertEquals("http://localhost:8080/starscan/starships?page=3&limit=10", response.getNext());
    }

    @Test
    public void testGetByName() {
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        result.put("name", "Millennium Falcon");
        result.put("url", "someUrl");
        mockResponse.put("result", Collections.singletonList(result));

        Starship starship = new Starship();
        StarshipProperties properties = new StarshipProperties();
        properties.setName("Millennium Falcon");
        properties.setUrl("someUrl");
        starship.setProperties(properties);

        when(objectMapper.convertValue(anyMap(), eq(Starship.class))).thenReturn(starship);

        Starship retrievedStarship = starshipsService.getByName("Millennium Falcon", request);

        assertNotNull(retrievedStarship);
        assertEquals("Millennium Falcon", retrievedStarship.getProperties().getName());
        assertEquals("http://localhost:8080/starscan/starships/Millennium Falcon", retrievedStarship.getProperties().getUrl());
    }

    @Test
    public void testGetByNameNotFound() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("result", Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> starshipsService.getByName("Nonexistent Name", request));
    }

}