package com.web.core.configuration;

public class ConfigSchedule {

    public static class rateTimeApiService {
        public static final String connectCMSRate = "2000";
        public static final String updateCMSRate = "10000";
        public static final String reloadConfigRate = "0 0 0 * * *";
    }
}
