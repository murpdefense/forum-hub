package br.com.soupaulodev.forumhub.filters;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

public class RateLimitFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternal_withValidRequest_shouldProceed() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("Authorization", "valid_jwt_token")});

        when(jwtUtil.extractUsername("valid_jwt_token")).thenReturn("user1");

        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(valueOperationsMock.get(anyString())).thenReturn(null);  // Simulating that rate limit information is absent in Redis.

        Bucket bucketMock = mock(Bucket.class);
        when(bucketMock.tryConsume(1)).thenReturn(true);

        rateLimitFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }


    @Test
    public void testDoFilterInternal_withMissingJwtToken_shouldThrowUnauthorizedException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        Bucket bucketMock = mock(Bucket.class);
        when(bucketMock.tryConsume(1)).thenReturn(true);

        when(request.getCookies()).thenReturn(new Cookie[]{});

        try {
            rateLimitFilter.doFilterInternal(request, response, chain);
        } catch (UnauthorizedException e) {
            return;
        }
        throw new AssertionError("Expected UnauthorizedException");
    }

    @Test
    public void testDoFilterInternal_withInvalidJwtToken_shouldThrowUnauthorizedException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        Bucket bucketMock = mock(Bucket.class);
        when(bucketMock.tryConsume(1)).thenReturn(true);

        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("Authorization", "invalid_jwt_token")});
        when(jwtUtil.extractUsername("invalid_jwt_token")).thenThrow(new RuntimeException("Invalid token"));

        try {
            rateLimitFilter.doFilterInternal(request, response, chain);
        } catch (UnauthorizedException e) {
            return;
        }
        throw new AssertionError("Expected UnauthorizedException");
    }
}
