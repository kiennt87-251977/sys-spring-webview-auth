package com.auth.core.schedule.constant;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * The type Constants.
 */
public class Constants {

    /**
     * The type Service cache.
     */
    public static class ServiceCache {
        /**
         * The constant username.
         */
//    ***************************
        //basic auth for updateCache (center_service <=> services)
        public static String username = "service_center";
        /**
         * The constant password.
         */
        public static String password = "9999service_center9999";
        private static String encryptAuthCache = "";

        /**
         * The constant keylastTimeUpdate.
         */
//    ***************************
        // key in cache ServiceCache.mapManager (center_service & services)
        public static String keylastTimeUpdate = "last_time_update";
        /**
         * The constant keylastTimeUpdateText.
         */
        public static String keylastTimeUpdateText = "last_time_update_text";
        /**
         * The constant keyLastTimeResetCache.
         */
        public static String keyLastTimeResetCache = "last_time_reset_cache";
        /**
         * The constant keyLastTimeResetCacheText.
         */
        public static String keyLastTimeResetCacheText = "last_time_reset_cache_text";
        /**
         * The constant keylastTimeServiceReceiveRequest.
         */
        public static String keylastTimeServiceReceiveRequest = "last_time_service_receive_request";
        /**
         * The constant keyAvailable.
         */
        public static String keyAvailable = "available_cloud_server";


        /**
         * The constant keyCheckConnect.
         */
        public static String keyCheckConnect = "check_connect";
        /**
         * The constant keyServiceStartTime.
         */
        public static String keyServiceStartTime = "service_start_time";
        /**
         * The constant keyExceptionHistoryService.
         */
        public static String keyExceptionHistoryService = "exception_history_service";

        /**
         * The constant keyServiceMemoryStats.
         */
        public static String keyServiceMemoryStats = "service_memory_stats";


        /**
         * The constant keyStatus.
         */
//    ***************************
        // key in response from (center_service => services)
        public static String keyStatus = "keyStatus";
        /**
         * The constant keyRestartCache.
         */
        public static String keyRestartCache = "keyRestartCache";
    }


    /**
     * The enum Type.
     */
    public enum TYPE {
        /**
         * Numb request entry type.
         */
        NUMB_REQUEST_ENTRY("numb_request_entry", 1),
        /**
         * Numb request success type.
         */
        NUMB_REQUEST_SUCCESS("numb_request_success", 2),
        /**
         * Numb request fail type.
         */
        NUMB_REQUEST_FAIL("numb_request_fail", 3),
        /**
         * Numb request current type.
         */
        NUMB_REQUEST_CURRENT("numb_request_current", 4),
        ;

        private String name;
        private Integer numb;

        TYPE(String name, Integer numb) {
            this.name = name;
            this.numb = numb;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets numb.
         *
         * @return the numb
         */
        public Integer getNumb() {
            return numb;
        }
    }


    /**
     * Is key in cache service cache map manager boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean isKeyInCacheServiceCacheMapManager(String key) {
        if (ServiceCache.keylastTimeUpdate.equals(key)
                || ServiceCache.keylastTimeUpdateText.equals(key)
                || ServiceCache.keyLastTimeResetCache.equals(key)
                || ServiceCache.keyLastTimeResetCacheText.equals(key)
                || ServiceCache.keyAvailable.equals(key)
                || ServiceCache.keyServiceMemoryStats.equals(key)
        ) {
            return true;
        }
        return false;
    }

    /**
     * Is key in cache service cms boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean isKeyInCacheServiceCMS(String key) {
        if (ServiceCache.keylastTimeServiceReceiveRequest.equals(key)
                || ServiceCache.keyCheckConnect.equals(key)
                || ServiceCache.keyExceptionHistoryService.equals(key)
                || ServiceCache.keyServiceStartTime.equals(key)
        ) {
            return true;
        }
        return false;
    }

    /**
     * Is key in type list boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean isKeyInTYPEList(String key) {
        TYPE[] list = TYPE.values();
        for (TYPE elem : list) {
            if (elem.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets encrypt auth cache.
     *
     * @param base64Credentials the base 64 credentials
     */
    public static void setEncryptAuthCache(String base64Credentials) {
        ServiceCache.encryptAuthCache = base64Credentials;
    }

    /**
     * Gets encrypt auth cache.
     *
     * @param base64Credentials the base 64 credentials
     * @return the encrypt auth cache
     */
    public static boolean getEncryptAuthCache(String base64Credentials) {
        boolean res = false;
        if (ServiceCache.encryptAuthCache != null && !ServiceCache.encryptAuthCache.equals("")) {
            res = ServiceCache.encryptAuthCache.equals(base64Credentials);
        } else {
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);
            res = ServiceCache.username.equals(values[0])
                    && ServiceCache.password.equals(values[1]);
        }

        return res;
    }
}
