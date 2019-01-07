package com.cgs.hb.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties

class RedisClien {

    private lateinit var jedis: RedisProperties.Jedis
    private lateinit var jedisPool: RedisProperties.Pool
}