package br.com.soupaulodev.forumhub.security.filters;

import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link JwtAuthenticationFilter}.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter filter;
    private JwtUtil jwtUtil;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        filter = new JwtAuthenticationFilter(userDetailsService, jwtUtil);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_SuccessfulAuthentication() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "valid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUserId("valid-token")).thenReturn(UUID.randomUUID().toString());
        when(jwtUtil.isTokenExpired("valid-token")).thenReturn(false);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be set");
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, SecurityContextHolder.getContext().getAuthentication(), "Authentication should be of type UsernamePasswordAuthenticationToken");
    }

    @Test
    void testDoFilterInternal_ExpiredToken() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "expired-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("expired-token")).thenThrow(new RuntimeException("Token expired"));

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for expired token");
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set when no token is present");
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "invalid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("invalid-token")).thenThrow(new IllegalArgumentException("Invalid token"));

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for invalid token");
    }

    @Test
    void testDoFilterInternal_ExceptionHandling() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "valid-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtUtil.extractUsername("valid-token")).thenThrow(new RuntimeException("Unexpected error"));

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set when an exception occurs");
    }
}
