package br.com.soupaulodev.forumhub.filters;

import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    private final int ipRateLimit = 10;
    private final int userRateLimit = 10;
    private final Duration refillDuration = Duration.ofMinutes(1);

    public RateLimitFilter(JwtUtil jwtUtil, StringRedisTemplate redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();

        if (!checkRateLimit(clientIp, ipRateLimit, response)) {
            return;
        }

        Optional<String> jwtToken = extractJwtFromCookies(request);
        if (jwtToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing or invalid token.");
            return;
        }

        String username;
        try {
            username = jwtUtil.extractUsername(jwtToken.get());
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Invalid token.");
            return;
        }

        if (!checkRateLimit(username, userRateLimit, response)) {
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean checkRateLimit(String key, int limit, HttpServletResponse response) throws IOException {
        // Cria um bucket persistido no Redis para a chave fornecida
        Bucket bucket = createOrGetBucket(key, limit);

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded for " + key + ". Try again later.");
            return false;
        }
    }

    private Bucket createOrGetBucket(String key, int capacity) {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, refillDuration));

        return Bucket.builder()
//                .withRedisTemplate(redisTemplate)
//                .withKey(key)
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
}}