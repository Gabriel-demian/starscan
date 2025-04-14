package com.galaxydata.starscan.util;

import com.galaxydata.starscan.exception.InvalidSwapiUrlException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UrlUtilTest {

    @Test
    public void testAdaptSwapiPageUrlWithValidUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("localhost");
        Mockito.when(request.getServerPort()).thenReturn(8080);

        String swapiUrl = "https://swapi.dev/api/people/?page=2&limit=10";
        String path = "/starscan/people";
        String expectedUrl = "http://localhost:8080/starscan/people?page=2&limit=10";

        String actualUrl = UrlUtil.adaptSwapiPageUrl(swapiUrl, request, path);
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testAdaptSwapiPageUrlWithNullUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String path = "/starscan/people";
        String actualUrl = UrlUtil.adaptSwapiPageUrl(null, request, path);
        assertNull(actualUrl);
    }

    @Test
    public void testAdaptSwapiPageUrlWithInvalidUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String swapiUrl = "invalid url";
        String path = "/starscan/people";

        assertThrows(InvalidSwapiUrlException.class, () -> UrlUtil.adaptSwapiPageUrl(swapiUrl, request, path));
    }

    @Test
    public void testAdaptSwapiPageUrlWithRelativeUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String swapiUrl = "/api/people/?page=2&limit=10";
        String path = "/starscan/people";

        assertThrows(InvalidSwapiUrlException.class, () -> UrlUtil.adaptSwapiPageUrl(swapiUrl, request, path));
    }

    @Test
    public void testGetBaseUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("https");
        Mockito.when(request.getServerName()).thenReturn("example.com");
        Mockito.when(request.getServerPort()).thenReturn(8443);

        String expectedBaseUrl = "https://example.com:8443";
        String actualBaseUrl = UrlUtil.getBaseUrl(request);
        assertEquals(expectedBaseUrl, actualBaseUrl);
    }
}