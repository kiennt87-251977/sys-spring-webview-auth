package com.web.core.domain.constant;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Constants {

    public static class ServiceCache {
        //    ***************************
        //basic auth for updateCache (center_service <=> services)
        public static String username = "service_center";
        public static String password = "9999service_center9999";
        private static String encryptAuthCache = "";

        //    ***************************
        // key in cache ServiceCache.mapManager (center_service & services)
        public static String keylastTimeUpdate = "last_time_update";
        public static String keylastTimeUpdateText = "last_time_update_text";
        public static String keyLastTimeResetCache = "last_time_reset_cache";
        public static String keyLastTimeResetCacheText = "last_time_reset_cache_text";
        public static String keylastTimeServiceReceiveRequest = "last_time_service_receive_request";
        public static String keyAvailable = "available_cloud_server";


        public static String keyCheckConnect = "check_connect";
        public static String keyServiceStartTime = "service_start_time";
        public static String keyExceptionHistoryService = "exception_history_service";

        public static String keyServiceMemoryStats = "service_memory_stats";


        //    ***************************
        // key in response from (center_service => services)
        public static String keyStatus = "keyStatus";
        public static String keyRestartCache = "keyRestartCache";
    }


    public enum TYPE {
        NUMB_REQUEST_ENTRY("numb_request_entry", 1),
        NUMB_REQUEST_SUCCESS("numb_request_success", 2),
        NUMB_REQUEST_FAIL("numb_request_fail", 3),
        NUMB_REQUEST_CURRENT("numb_request_current", 4),
        ;

        private String name;
        private Integer numb;

        TYPE(String name, Integer numb) {
            this.name = name;
            this.numb = numb;
        }

        public String getName() {
            return name;
        }

        public Integer getNumb() {
            return numb;
        }
    }


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

    public static boolean isKeyInTYPEList(String key) {
        TYPE[] list = TYPE.values();
        for (TYPE elem : list) {
            if (elem.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static void setEncryptAuthCache(String base64Credentials) {
        ServiceCache.encryptAuthCache = base64Credentials;
    }

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
