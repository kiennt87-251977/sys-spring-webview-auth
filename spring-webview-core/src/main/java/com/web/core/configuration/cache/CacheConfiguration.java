package com.web.core.configuration.cache;//package com.viettel.core.configuration.cache;
//
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.spring.cache.HazelcastCacheManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableCaching
//@Slf4j
//public class CacheConfiguration extends HazelcastConfiguration {
//
//    @Bean(name = "hazelcastCacheManager")
//    public CacheManager hazelcastCacheManager(@Qualifier("myHazelcast") HazelcastInstance hazelcastInstance) {
//        log.debug("Starting HazelcastCacheManager!");
//        return new HazelcastCacheManager(hazelcastInstance);
//    }
//
//
//
//}
