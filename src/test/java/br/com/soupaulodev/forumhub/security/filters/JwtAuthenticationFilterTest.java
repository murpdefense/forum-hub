package br.com.soupaulodev.forumhub.security.filters;

import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter filter;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        userDetailsService = mock(UserDetailsService.class);
        filter = new JwtAuthenticationFilter(userDetailsService, jwtUtil);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_SuccessfulAuthentication() throws ServletException, IOException {
        // Arrange
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "valid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("valid-token")).thenReturn("testuser");
        when(jwtUtil.isTokenExpired("valid-token")).thenReturn(false);
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be set");
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName(), "Username should match");
    }

    @Test
    void testDoFilterInternal_ExpiredToken() throws ServletException, IOException {
        // Arrange
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "expired-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("expired-token")).thenThrow(new RuntimeException("Token expired"));

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for expired token");
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        // Arrange
        when(request.getCookies()).thenReturn(null);

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set when no token is present");
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        // Arrange
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "invalid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("invalid-token")).thenThrow(new IllegalArgumentException("Invalid token"));

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for invalid token");
    }

    @Test
    void testDoFilterInternal_ExceptionHandling() throws ServletException, IOException {
        // Arrange
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "valid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("valid-token")).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set when an exception occurs");
    }
}
