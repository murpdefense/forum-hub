package br.com.soupaulodev.forumhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis Configuration Class.
 * <p>
 * This class defines the configuration for Redis in the application.
 * It creates a {@link StringRedisTemplate} bean to handle Redis operations.
 * </p>
 *
 * Key features:
 * - Creates a {@link StringRedisTemplate} bean to handle Redis operations.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}