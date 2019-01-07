package javaDiffKotlin

import java.lang.reflect.Method
import java.time.Duration
import java.util.HashMap

import com.cgs.hb.config.RedisConfig
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper

import javax.annotation.Resource

@Configuration
@EnableCaching
class RedisDiff : CachingConfigurerSupport() {

    @Value("\${spring.redis.host}")
    private val host: String? = null
    @Value("\${spring.redis.port}")
    private val port: Int = 0
    @Value("\${spring.redis.timeout}")
    private val timeout: Int = 0
    @Value("\${cacheNames}")
    private val cacheNames: String? = null

    @Resource
    private val lettuceConnectionFactory: LettuceConnectionFactory? = null

    /*
     * 定义缓存数据 key 生成策略的bean 包名+类名+方法名+所有参数
     */
    @Bean
    override fun keyGenerator(): KeyGenerator? {
        return KeyGenerator { target, method, params ->
            val sb = StringBuilder()
            sb.append(target.javaClass.name)
            sb.append(method.name)
            for (obj in params) {
                sb.append(obj.toString())
            }
            sb.toString()
        }
    }

    @Bean
    fun redisConnectionFactory(): JedisConnectionFactory {
        val factory = JedisConnectionFactory()
        factory.setHostName(host)
        factory.port = port
        factory.timeout = timeout // 设置连接超时时间
        return factory
    }

    // 缓存管理器
    // 启用spring的缓存支持
    @Bean
    override fun cacheManager(): CacheManager? {
        //重启项目时，清空当前数据库缓存数据
        lettuceConnectionFactory!!.connection.flushDb()

        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        // 设置缓存过期时间sec
        val map = HashMap<String, RedisCacheConfiguration>()
        if (StringUtils.isNotBlank(cacheNames)) {
            val strings = cacheNames!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in strings) {
                if (StringUtils.isNotBlank(s)) {
                    val str = s.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    // 添加指定缓存过期时间，key：缓存名称，value：过期时间（秒）
                    map[str[0]] = redisCacheConfiguration.entryTtl(Duration.ofSeconds(3))
                }
            }
        }
        val builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
                .withInitialCacheConfigurations(map)
        return builder.build()
    }

    // 1.项目启动时此方法先被注册成bean被spring管理
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = StringRedisTemplate(factory)
        setSerializer(template)// 设置序列化工具
        template.afterPropertiesSet()
        return template
    }

    /**
     * 序列化工具
     *
     * @param template
     */
    private fun setSerializer(template: StringRedisTemplate) {
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        template.valueSerializer = jackson2JsonRedisSerializer
    }
}
