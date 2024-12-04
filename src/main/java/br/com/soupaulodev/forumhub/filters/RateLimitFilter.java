package br.com.soupaulodev.forumhub.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 100;
    private static final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1);
    private static final Cache<String, RequestInfo> requestCache = new Cache<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();

        if (isRateLimited(clientIp)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests, please try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isRateLimited(String clientIp) {
        RequestInfo requestInfo = requestCache.get(clientIp);

        if (requestInfo == null) {
            requestInfo = new RequestInfo(0, System.currentTimeMillis());
        }

        long timePassed = System.currentTimeMillis() - requestInfo.getTimestamp();
        if (timePassed > TIME_WINDOW) {
            requestInfo.setCount(0);
            requestInfo.setTimestamp(System.currentTimeMillis());
        }

        if (requestInfo.getCount() >= MAX_REQUESTS) {
            return true;
        }

        requestInfo.setCount(requestInfo.getCount() + 1);
        requestCache.put(clientIp, requestInfo);
        return false;
    }

    /**
     * Cache implementation for storing request information.
     * This implementation should be replaced with a more robust solution in a production environment.
     */
    private static class Cache<K, V> {
        private final java.util.Map<K, V> map = new java.util.HashMap<>();

        public V get(K key) {
            return map.get(key);
        }

        public void put(K key, V value) {
            map.put(key, value);
        }
    }

    /**
     * Class to store request information per client IP.
     */
    private static class RequestInfo {
        private int count;
        private long timestamp;

        public RequestInfo(int count, long timestamp) {
            this.count = count;
            this.timestamp = timestamp;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
