package com.cheong.clinic_api.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

//	@Bean
//	CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
//					.prefixCacheNameWith("app.")
//					.entryTtl(Duration.ofHours(1))
//					.disableCachingNullValues();
//		
//		return RedisCacheManager.builder(connectionFactory)
//						.cacheDefaults(configuration)
//						.build();
//	}
}
