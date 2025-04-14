package com.galaxydata.starscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxydata.starscan.dto.Person;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

public class SwapiPeopleServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SwapiPeopleService peopleService;

    private MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        peopleService.swapiMainUrl = "https://www.swapi.tech/api";
    }

    @Test
    public void testGetList() {
        SwapiListResponse mockResponse = new SwapiListResponse();
        SwapiListResponse.Results result = new SwapiListResponse.Results();
        result.setUid("1");
        mockResponse.setResults(Collections.singletonList(result));
        mockResponse.setPrevious("https://www.swapi.tech/people/?page=1&limit=10");
        mockResponse.setNext("https://www.swapi.tech/people/?page=3&limit=10");

        SwapiListResponse response = peopleService.getList(2, 10, request);

        assertNotNull(response);
        assertEquals(10, response.getResults().size());
        assertEquals("http://localhost:8080/people/11", response.getResults().get(0).getUrl());
        assertEquals("http://localhost:8080/people?page=1&limit=10", response.getPrevious());
        assertEquals("http://localhost:8080/people?page=3&limit=10", response.getNext());
    }

    @Test
    public void testGetById() {

        Person person = new Person();
        Person.PersonProperties properties = new Person.PersonProperties();
        properties.setName("Luke Skywalker");

        properties.setUrl("https://www.swapi.tech/api/people/1");
        person.setProperties(properties);

        when(objectMapper.convertValue(anyMap(), eq(Person.class))).thenReturn(person);

        Person retrievedPerson = peopleService.getById("1", request);

        assertNotNull(retrievedPerson);
        assertEquals("Luke Skywalker", retrievedPerson.getProperties().getName());
        assertEquals("http://localhost:8080/people/1", retrievedPerson.getProperties().getUrl());

    }

    @Test
    public void testGetByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> peopleService.getById("0", request));
    }

    @Test
    public void testGetByName() {
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        result.put("name", "Luke Skywalker");
        result.put("url", "someUrl");
        mockResponse.put("result", Collections.singletonList(result));

        Person person = new Person();
        Person.PersonProperties properties = new Person.PersonProperties();
        properties.setName("Luke Skywalker");
        properties.setUrl("someUrl");
        person.setProperties(properties);

        when(objectMapper.convertValue(anyMap(), eq(Person.class))).thenReturn(person);

        Person retrievedPerson = peopleService.getByName("Luke Skywalker", request);

        assertNotNull(retrievedPerson);
        assertEquals("Luke Skywalker", retrievedPerson.getProperties().getName());
        assertEquals("http://localhost:8080/people/Luke Skywalker", retrievedPerson.getProperties().getUrl());
    }

    @Test
    public void testGetByNameNotFound() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("result", Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> peopleService.getByName("Nonexistent Name", request));
    }
}