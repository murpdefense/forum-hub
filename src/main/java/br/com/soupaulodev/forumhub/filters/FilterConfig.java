package br.com.soupaulodev.forumhub.filters;


import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Filter Configuration Class.
 * <p>
 * This class defines the configuration for filters in the application, such as the rate-limiting filter.
 * It creates and registers the rate-limiting filter with the Spring context, allowing it to be applied to specific endpoints.
 * </p>
 *
 * Key features:
 * - Creates a {@link RateLimitFilter} bean to handle rate limiting for API requests.
 * - Registers the rate-limiting filter with the Spring context to apply it to specific URL patterns.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Configuration
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    public FilterConfig(JwtUtil jwtUtil, StringRedisTemplate redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public RateLimitFilter rateLimitFilter() {
        return new RateLimitFilter(jwtUtil, redisTemplate);
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration(RateLimitFilter rateLimitFilter) {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(rateLimitFilter);
        registrationBean.addUrlPatterns("/api/v1/**");
        return registrationBean;
    }
}