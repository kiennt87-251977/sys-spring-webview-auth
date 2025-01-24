package com.auth.core.schedule.config;

/**
 * The type Config schedule service.
 */
public class ConfigScheduleService {

    /**
     * The type Rate time api service.
     */
    public static class rateTimeApiService {
        /**
         * The constant connectCMSRate.
         */
        public static final String connectCMSRate = "2000";
        /**
         * The constant updateCMSRate.
         */
        public static final String updateCMSRate = "10000";
        /**
         * The constant reloadConfigRate.
         */
        public static final String reloadConfigRate = "0 0 0 * * *";
    }
}
