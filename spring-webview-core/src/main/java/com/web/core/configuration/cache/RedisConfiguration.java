package com.web.core.configuration.cache;//package com.viettel.core.configuration.cache;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@Slf4j
//@ConfigurationProperties(prefix = "spring.redis.cluster")
//public class RedisConfiguration {
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory);
//        return template;
//    }
//
////************************************************************************************
////    @Bean
////    JedisConnectionFactory jedisConnectionFactory() {
////        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
////        redisStandaloneConfiguration.setPassword(RedisPassword.of("RedisPassword"));
////        return new JedisConnectionFactory(redisStandaloneConfiguration);
////    }
////************************************************************************************
//
//
////    /**
////     * Lettuce
////     */
////    @Bean
////    public RedisConnectionFactory lettuceConnectionFactory() {
////        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
////                .master("mymaster")
////                .sentinel("127.0.0.1", 26379)
////                .sentinel("127.0.0.1", 26380);
////        return new LettuceConnectionFactory(sentinelConfig);
////    }
////
////    /**
////     * Jedis
////     */
////    @Bean
////    public RedisConnectionFactory jedisConnectionFactory() {
////        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
////                .master("mymaster")
////                .sentinel("127.0.0.1", 26379)
////                .sentinel("127.0.0.1", 26380);
////        return new JedisConnectionFactory(sentinelConfig);
////    }
////************************************************************************************
//
//
//    /*
//     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
//     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
//     * ...
//     */
//    List<String> nodes;
//
//    public List<String> getNodes() {
//        return nodes;
//    }
//
//    public void setNodes(List<String> nodes) {
//        this.nodes = nodes;
//    }
//
//    @Bean
//    RedisConnectionFactory jedisConnectionFactory() {
//        return new LettuceConnectionFactory(
//                new RedisClusterConfiguration(nodes));
//    }
//
//}
