package com.web.registry.config;

public class ConfigScheduleService {

    public static class rateTimeApiService {
        public static final String connectCMSRate = "2000";
        public static final String updateCMSRate = "30000";
        public static final String reloadConfigRate = "0 0 0 * * *";
    }
}
