package com.auth.core.schedule.cache;


import com.auth.core.utils.DateUtilsV8;
import com.auth.core.schedule.constant.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Service cache.
 */
public class ServiceCache {

    private static int min = 100;
    private static int max = 999;
    /**
     * The constant numbService.
     */
    public static Integer numbService = new Random().nextInt((max - min) + 1) + min;


    /**
     * The constant messageExceptionHistory.
     */
    public static String messageExceptionHistory = "";

    /**
     * The constant serviceStartTime.
     */
    public static final Date serviceStartTime = new Date();

    /**
     * The constant serviceApplicationName.
     */
    public static String serviceApplicationName = "";
    /**
     * The constant serviceName.
     */
    public static String serviceName = "";
    /**
     * The constant serviceIp.
     */
    public static String serviceIp = "";
    /**
     * The constant servicePort.
     */
    public static String servicePort = "";

    /**
     * The constant serviceMemoryStats.
     */
    public static String serviceMemoryStats = "";


    //    ***************************

    /**
     * The constant messageCheckConnect.
     */
    public static String messageCheckConnect = "";
    /**
     * The constant lastTimeServiceReceiveRequest.
     */
    public static Date lastTimeServiceReceiveRequest = null;


    private static Map<String, AtomicInteger> mapManager = new HashMap<>();
    /**
     * The constant listManagerResponse.
     */
    public static Set<String> listManagerResponse = new HashSet<>();


    //    *********************************************************************************************

    static {
        initMapManager();
    }

    //    *********************************************************************************************

    /**
     * Init value map manager.
     */
    public synchronized static void initValueMapManager() {
        Constants.TYPE[] list = Constants.TYPE.values();
        for (Constants.TYPE elem : list) {
            mapManager.get(elem.getName()).getAndSet(0);
        }
        for (String elem : listManagerResponse) {
            mapManager.get(elem).getAndSet(0);
        }
    }

    private synchronized static void initMapManager() {
        Constants.TYPE[] list = Constants.TYPE.values();
        for (Constants.TYPE elem : list) {
            createKeyMap(elem.getName());
        }
    }

    private static void createKeyMap(String key) {
        if (mapManager.get(key) == null) {
            mapManager.put(key, new AtomicInteger());
        }
    }

    /**
     * Gets numb request by key string.
     *
     * @param key the key
     * @return the numb request by key string
     */
    public static Integer getNumbRequestByKeyString(String key) {
        return mapManager.get(key).intValue();
    }

    /**
     * Gets numb request.
     *
     * @param key the key
     * @return the numb request
     */
    public static Integer getNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).intValue();
    }

    /**
     * Increment numb request integer.
     *
     * @param key the key
     * @return the integer
     */
    public static Integer incrementNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).incrementAndGet();
    }

    /**
     * Decrement numb request integer.
     *
     * @param key the key
     * @return the integer
     */
    public static Integer decrementNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).decrementAndGet();
    }

    /**
     * Increment numb by response integer.
     *
     * @param key the key
     * @return the integer
     */
//    *********************************************************************************************
    public static Integer incrementNumbByResponse(String key) {
        if (key == null) {
            key = "null";
        }
        initValueMapManagerResponse(key);
        return mapManager.get(key).incrementAndGet();
    }

    private static void initValueMapManagerResponse(String key) {
        if (mapManager.get(key) == null) {
            synchronized (ServiceCache.class) {
                if (mapManager.get(key) == null) {
                    mapManager.put(key, new AtomicInteger());
                    listManagerResponse.add(key);
                }
            }
        }
    }

    private static boolean inTypeKey(String key) {
        Constants.TYPE[] list = Constants.TYPE.values();
        for (Constants.TYPE elem : list) {
            if (elem.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    //    *********************************************************************************************

    /**
     * Update cache service.
     *
     * @param mapContent the map content
     */
    public static void updateCacheService(Map<String, Object> mapContent) {

        Set<String> listKey = mapContent.keySet();
        for (String elem : listKey) {
            if (!Constants.isKeyInCacheServiceCacheMapManager(elem)
            ) {
                try {
                    if (Constants.ServiceCache.keylastTimeServiceReceiveRequest.equals(elem)) {
                        try {
                            lastTimeServiceReceiveRequest = DateUtilsV8.convertStringToDate(String.valueOf(mapContent.get(elem)), DateUtilsV8.regex01);
                        } catch (Exception e) {
                        }
                    } else if (Constants.ServiceCache.keyExceptionHistoryService.equals(elem)) {
                        try {
                            messageExceptionHistory = String.valueOf(mapContent.get(elem));
                        } catch (Exception e) {
                        }
                    } else {
                        Integer value = (Integer) mapContent.get(elem);
                        if (mapManager.get(elem) == null) {
                            mapManager.put(elem, new AtomicInteger());
                        }
                        mapManager.get(elem).set(value);
                        if (!inTypeKey(elem)) {
                            listManagerResponse.add(elem);
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * Update check connect.
     *
     * @param message the message
     */
//    *********************************************************************************************
    public static void updateCheckConnect(String message) {
        messageCheckConnect = message;
    }

    /**
     * Update last time service receive request.
     */
//    *********************************************************************************************
    public static void updateLastTimeServiceReceiveRequest() {
        lastTimeServiceReceiveRequest = new Date();
    }

    /**
     * Update message exception history.
     *
     * @param e the e
     */
//    *********************************************************************************************
    public static void updateMessageExceptionHistory(Exception e) {
        if (messageExceptionHistory.length() > 200) {
            messageExceptionHistory = "";
        }
        messageExceptionHistory += " | " + e.getMessage();
    }

    /**
     * Update service memory stats.
     */
//    *********************************************************************************************
    public static void updateServiceMemoryStats() {
        serviceMemoryStats = "Free Memory / Total Memory / Max Memory (Mbs) : " + String.valueOf(Runtime.getRuntime().freeMemory() / 1048576)
                + " / " + String.valueOf(Runtime.getRuntime().totalMemory() / 1048576)
                + " / " + String.valueOf(Runtime.getRuntime().maxMemory() / 1048576)
        ;
    }


    /**
     * Gets log start time.
     *
     * @return the log start time
     */
//    *********************************************************************************************
    public static String getLogStartTime() {
        String during = "(s) : ";
        int dure = Math.toIntExact(((new Date()).getTime() - serviceStartTime.getTime()) / 1000);
        if (dure > 59) {
            during = "(mn) : ";
            dure = dure / 60;
            if (dure > 59) {
                during = "(h) : ";
                dure = dure / 60;
                if (dure > 23) {
                    during = "(day) : ";
                    dure = dure / 24;
                }
            }
        }

        return serviceStartTime + " (during " + during + dure + " )";
    }
    //    *********************************************************************************************
}
