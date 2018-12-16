package com.qcz.qmplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.qcz.qmplatform.common.redis.RedisCache;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 缓存配置
 * @author changzhongq
 * @time 2018年8月2日 下午3:08:42
 */
@Configuration
public class RedisConfigurer implements EnvironmentAware {

	@Autowired
	private Environment env;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(env.getProperty("spring.redis.host"));
		redisStandaloneConfiguration.setPort(Integer.valueOf(env.getProperty("spring.redis.port")));
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfiguration = (JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		// 连接池配置
		JedisPoolConfig pool = new JedisPoolConfig();
		pool.setMaxTotal(200);
		pool.setMaxIdle(50);
		pool.setMinIdle(8);// 设置最小空闲数  
		pool.setMaxWaitMillis(10000);
		pool.setTestOnBorrow(true);
		pool.setTestOnReturn(true);
		// Idle时进行连接扫描  
		pool.setTestWhileIdle(true);
		// 表示idle object evitor两次扫描之间要sleep的毫秒数  
		pool.setTimeBetweenEvictionRunsMillis(30000);
		// 表示idle object evitor每次扫描的最多的对象数  
		pool.setNumTestsPerEvictionRun(10);
		// 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义  
		pool.setMinEvictableIdleTimeMillis(60000);
		jedisClientConfiguration.poolConfig(pool);
		// 构造连接工厂
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());// 这里不要使用StringRedisSerializer()，调用RedisCache.remove(K
																					// k)时k为一个对象参数
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public RedisCache<String, Object> redisCache(RedisTemplate<String, Object> redisTemplate) {
		return new RedisCache<String, Object>(redisTemplate);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
}
