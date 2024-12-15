package br.com.soupaulodev.forumhub.filters;

import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Filter Config Test.
 * <p>
 *      Unit tests for {@link FilterConfig}.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class FilterConfigTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private StringRedisTemplate redisTemplate;

    private FilterConfig filterConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterConfig = new FilterConfig(jwtUtil, redisTemplate);
    }

    @Test
    void testRateLimitFilterBeanCreation() {
        RateLimitFilter rateLimitFilter = filterConfig.rateLimitFilter();
        assertThat(rateLimitFilter).isNotNull();
        assertThat(rateLimitFilter).isInstanceOf(RateLimitFilter.class);
    }

    @Test
    void testRateLimitFilterRegistration() {
        RateLimitFilter mockRateLimitFilter = Mockito.mock(RateLimitFilter.class);
        FilterRegistrationBean<RateLimitFilter> registrationBean = filterConfig.rateLimitFilterRegistration(mockRateLimitFilter);
        assertThat(registrationBean).isNotNull();
        assertThat(registrationBean.getFilter()).isEqualTo(mockRateLimitFilter);
        assertThat(registrationBean.getUrlPatterns()).containsExactly("/api/v1/**");
    }
}
