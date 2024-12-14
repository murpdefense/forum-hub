package br.com.soupaulodev.forumhub.security.utils;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.security.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CookieUtilTest {

    private CustomUserDetailsService userDetailsService;
    private CookieUtil cookieUtil;

    @BeforeEach
    void setUp() {
        userDetailsService = Mockito.mock(CustomUserDetailsService.class);
        cookieUtil = new CookieUtil(userDetailsService);
    }

    @Test
    void testGenerateCookieWithToken_Success() {
        UUID uuid = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(uuid);
        user.setUsername("testuser");

        String fakeJwtToken = "fake-jwt-token";
        when(userDetailsService.generateToken(user)).thenReturn(fakeJwtToken);

        Cookie cookie = cookieUtil.generateCookieWithToken(user);

        assertNotNull(cookie, "Cookie should not be null");
        assertEquals("JWT_TOKEN", cookie.getName(), "Cookie name should be JWT_TOKEN");
        assertEquals(fakeJwtToken, cookie.getValue(), "Cookie value should match the generated JWT token");
        assertTrue(cookie.isHttpOnly(), "Cookie should be HTTP-only");
        assertTrue(cookie.getSecure(), "Cookie should be secure");
        assertEquals(60 * 60 * 24 * 7, cookie.getMaxAge(), "Cookie max age should be 7 days in seconds");
        assertEquals("/", cookie.getPath(), "Cookie path should be set to root");

        verify(userDetailsService, times(1)).generateToken(user);
    }

    @Test
    void testGenerateCookieWithToken_NullUser() {
        UserEntity user = null;

        assertThrows(NullPointerException.class, () -> cookieUtil.generateCookieWithToken(user), "Should throw NullPointerException when user is null");
        verify(userDetailsService, never()).generateToken(any());
    }

    @Test
    void testGenerateCookieWithToken_EmptyToken() {
        UUID uuid = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(uuid);
        user.setUsername("testuser");

        when(userDetailsService.generateToken(user)).thenReturn("");

        Cookie cookie = cookieUtil.generateCookieWithToken(user);

        assertNotNull(cookie, "Cookie should not be null");
        assertEquals("JWT_TOKEN", cookie.getName(), "Cookie name should be JWT_TOKEN");
        assertEquals("", cookie.getValue(), "Cookie value should be empty");
        assertTrue(cookie.isHttpOnly(), "Cookie should be HTTP-only");
        assertTrue(cookie.getSecure(), "Cookie should be secure");
        assertEquals(60 * 60 * 24 * 7, cookie.getMaxAge(), "Cookie max age should be 7 days in seconds");
        assertEquals("/", cookie.getPath(), "Cookie path should be set to root");

        verify(userDetailsService, times(1)).generateToken(user);
    }
}