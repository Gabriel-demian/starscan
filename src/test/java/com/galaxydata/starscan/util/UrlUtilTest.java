package com.galaxydata.starscan.util;

import com.galaxydata.starscan.exception.InvalidSwapiUrlException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class UrlUtilTest {

    @Test
    void testAdaptSwapiPageUrl_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        String swapiUrl = "https://swapi.dev/api/people/?page=2";
        String path = "/people";

        String adaptedUrl = UrlUtil.adaptSwapiPageUrl(swapiUrl, request, path);

        assertEquals("http://localhost:8080/people?page=2", adaptedUrl);
    }

    @Test
    void testAdaptSwapiPageUrl_NullInput() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String adaptedUrl = UrlUtil.adaptSwapiPageUrl(null, request, "/people");

        assertNull(adaptedUrl);
    }

    @Test
    void testAdaptSwapiPageUrl_InvalidUrl() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String invalidUrl = "invalid-url";

        assertThrows(InvalidSwapiUrlException.class, () ->
                UrlUtil.adaptSwapiPageUrl(invalidUrl, request, "/people")
        );
    }

    @Test
    void testAdaptSwapiPageUrl_UriSyntaxException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String invalidUrl = "http://[invalid-url]"; // Invalid URL that causes URISyntaxException

        InvalidSwapiUrlException exception = assertThrows(InvalidSwapiUrlException.class, () ->
                UrlUtil.adaptSwapiPageUrl(invalidUrl, request, "/people")
        );

        assertEquals("Invalid SWAPI URL: " + invalidUrl, exception.getMessage());
    }

    @Test
    void testGetBaseUrl_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("example.com");
        when(request.getServerPort()).thenReturn(443);

        String baseUrl = UrlUtil.getBaseUrl(request);

        assertEquals("https://example.com:443", baseUrl);
    }
}