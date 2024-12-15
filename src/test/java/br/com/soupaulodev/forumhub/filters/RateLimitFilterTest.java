package br.com.soupaulodev.forumhub.filters;

import br.com.soupaulodev.forumhub.modules.exception.usecase.RateLimitExceededException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RateLimitFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_RateLimitExceededByIp() throws Exception {
        request.setRemoteAddr("192.168.1.1");

        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.tryConsume(1)).thenReturn(false);

        rateLimitFilter = spy(rateLimitFilter);
        doReturn(mockBucket).when(rateLimitFilter).createOrGetBucket("192.168.1.1", 10);

        assertThrows(RateLimitExceededException.class, () -> {
            rateLimitFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    void testDoFilterInternal_RateLimitExceededByUser() throws Exception {
        request.setRemoteAddr("192.168.1.1");

        request.setCookies(new Cookie("Authorization", "fake-jwt-token"));
        when(jwtUtil.extractUsername("fake-jwt-token")).thenReturn("user123");

        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.tryConsume(1)).thenReturn(true);
        when(mockBucket.tryConsume(1)).thenReturn(false);

        rateLimitFilter = spy(rateLimitFilter);
        doReturn(mockBucket).when(rateLimitFilter).createOrGetBucket("192.168.1.1", 10);
        doReturn(mockBucket).when(rateLimitFilter).createOrGetBucket("user123", 10);

        assertThrows(RateLimitExceededException.class, () -> {
            rateLimitFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    void testDoFilterInternal_UnauthorizedRequest() throws Exception {
        request.setRemoteAddr("192.168.1.1");

        assertThrows(UnauthorizedException.class, () -> {
            rateLimitFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    void testDoFilterInternal_SuccessfulRequest() throws Exception {
        request.setRemoteAddr("192.168.1.1");

        request.setCookies(new Cookie("Authorization", "valid-jwt-token"));
        when(jwtUtil.extractUsername("valid-jwt-token")).thenReturn("user123");

        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.tryConsume(1)).thenReturn(true);
        when(mockBucket.tryConsume(1)).thenReturn(true);

        rateLimitFilter = spy(rateLimitFilter);
        doReturn(mockBucket).when(rateLimitFilter).createOrGetBucket("192.168.1.1", 10);
        doReturn(mockBucket).when(rateLimitFilter).createOrGetBucket("user123", 10);

        rateLimitFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
