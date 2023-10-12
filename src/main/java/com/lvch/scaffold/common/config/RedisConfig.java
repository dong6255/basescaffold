package com.lvch.scaffold.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * TITLE 使用lettuce作为redisTemplate底层连接器
 *
 * @author chunhelv
 * @date 2023-10-12 16:09
 * @description
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
public class RedisConfig {

    /**
     * TITLE 配置RedisTemplate
     *
     * @param lettuceConnectionFactory
     * @return RedisTemplate<String,Object>
     * @author chunhelv
     * @date 2023-10-12 16:09
     * @description
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer(Object.class);
        // 设置键（key）的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value序列化
        redisTemplate.setValueSerializer(serializer);
        // 设置HashKey序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置HashValue序列化
        redisTemplate.setHashValueSerializer(serializer);
        // 默认序列化
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        //打开事务支持
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}
