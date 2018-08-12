package com.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
	
	public static final String CACHE_USER = "users";
 
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_USER);
    }
}