package vn.alpaca.userservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setClientName("master")
                .setAddress("redis://127.0.0.1:6379");

        return Redisson.create(config);
    }

    @Bean
    public RedissonConnectionFactory
    redissonConnectionFactory(RedissonClient client) {
        return new RedissonConnectionFactory(client);
    }
}
