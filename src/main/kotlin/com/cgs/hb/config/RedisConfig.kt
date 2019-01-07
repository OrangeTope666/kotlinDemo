package com.cgs.hb.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import java.time.Duration
import javax.annotation.Resource

@Configuration
class RedisConfig : CachingConfigurerSupport() {

    @Value("\${spring.redis.host}")
    private lateinit var host: String
    @Value("\${spring.redis.port}")
    private var port: Int = 0
    @Value("\${spring.redis.timeout}")
    private var timeout: Duration = Duration.ZERO
    @Value("\${cacheNames}")
    private lateinit var cacheNames: String

    @Resource
    private val lcf: LettuceConnectionFactory? = null

    @Bean
    override fun keyGenerator(): KeyGenerator? {
        return KeyGenerator { target, method, params ->
            var sb = StringBuilder()
            sb.append(target.javaClass.name)
            sb.append(method.name)
            for (param in params) {
                sb.append(param.toString())
            }
            sb.toString()
        }
    }

    @Bean
    fun cacheManage(): CacheManager {
        //项目启动清空当前缓存数据
        lcf!!.connection.flushDb()
        var cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        var map = HashMap<String, RedisCacheConfiguration>()
        if (StringUtils.isNoneBlank(cacheNames)) {
            var strings = cacheNames.split(",")
            for (s in strings) {
                if (StringUtils.isNoneBlank(s)) {
                    var str = s.split(":")
                    map.put(str[0], cacheConfig.entryTtl(Duration.ofSeconds(str[1].toLong())))
                }
            }
        }
        val bulider = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(lcf)
                .withInitialCacheConfigurations(map)
        return bulider.build()
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, String> {
        var template = StringRedisTemplate(factory)
        setSerializer(template)
        template.afterPropertiesSet()
        return template
    }

    fun setSerializer(template: StringRedisTemplate) {
        var jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        var om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        template.valueSerializer = jackson2JsonRedisSerializer
    }
}