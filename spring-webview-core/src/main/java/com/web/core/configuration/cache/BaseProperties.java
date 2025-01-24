package com.web.core.configuration.cache;//package com.viettel.core.configuration.cache;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Setter
//@Getter
//@Component
//@ConfigurationProperties(
//        prefix = "base",
//        ignoreUnknownFields = false
//)
//public class BaseProperties {
//    private final Hazelcast hazelcast = new Hazelcast();
//
//    private final RateLimitService rateLimitService = new RateLimitService();
//
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class Hazelcast {
//        private int timeToLiveSeconds = 3600;
//        private int maxIdleSeconds = 0;
//        private int backupCount = 1;
//        private int port = 5701;
//        private int cpCountMember = 0;
//        private boolean multicast = false;
//        private boolean tcpIp = true;
//    }
//
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class RateLimitService {
//        private long rateLimit = Long.MAX_VALUE;
//        private long limitDuration = 1l;
//    }
//
//}
