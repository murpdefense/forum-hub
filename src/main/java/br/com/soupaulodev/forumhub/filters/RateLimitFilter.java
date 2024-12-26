package br.com.soupaulodev.forumhub.filters;

import br.com.soupaulodev.forumhub.modules.exception.usecase.RateLimitExceededException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

/**
 * Rate Limit Filter.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    @Value("${rate-limit.ip-limit}")
    private int ipRateLimit;
    @Value("${rate-limit.user-limit}")
    private int userRateLimit;
    private final Duration refillDuration = Duration.ofMinutes(1);

    public RateLimitFilter(JwtUtil jwtUtil, StringRedisTemplate redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        validateRateLimits();
    }

    private void validateRateLimits() {
        if (ipRateLimit <= 0) { ipRateLimit = 10; }
        if (userRateLimit <= 0) { userRateLimit = 10; }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        if (rateLimitCheck(clientIp, ipRateLimit, response)) {
            return;
        }

        Optional<String> jwtToken = extractJwtFromCookies(request);
        if (jwtToken.isEmpty()) {
            throw new UnauthorizedException("Unauthorized: Missing or invalid token.");
        }

        String userId;
        try {
            userId = jwtUtil.extractUserId(jwtToken.get());
        } catch (Exception e) {
            throw new UnauthorizedException("Unauthorized: Invalid token.");
        }

        if (rateLimitCheck(userId, userRateLimit, response)) {
            return;
        }

        chain.doFilter(request, response);
    }

    protected boolean rateLimitCheck(String key, int limit, HttpServletResponse response) {
        Bucket bucket = createOrGetBucket(key, limit);

        if (bucket.tryConsume(1)) {
            return false;
        } else {
            throw new RateLimitExceededException();
        }
    }

    protected Bucket createOrGetBucket(String key, int capacity) {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, refillDuration));

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Optional<String> extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        for (Cookie cookie : request.getCookies()) {
            if ("Authorization".equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }
}
