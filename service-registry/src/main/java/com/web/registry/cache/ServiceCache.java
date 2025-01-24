package com.web.registry.cache;



import com.web.registry.utils.DateUtilsV8;
import com.web.registry.constant.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceCache {

    private static int min = 100;
    private static int max = 999;
    public static Integer numbService = new Random().nextInt((max - min) + 1) + min;


    public static String messageExceptionHistory = "";

    public static final Date serviceStartTime = new Date();

    public static String serviceApplicationName = "";
    public static String serviceName = "";
    public static String serviceIp = "";
    public static String servicePort = "";

    public static String serviceMemoryStats = "";


    //    ***************************

    public static String messageCheckConnect = "";
    public static Date lastTimeServiceReceiveRequest = null;


    private static Map<String, AtomicInteger> mapManager = new HashMap<>();
    public static Set<String> listManagerResponse = new HashSet<>();


    //    *********************************************************************************************

    static {
        initMapManager();
    }

    //    *********************************************************************************************

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

    public static Integer getNumbRequestByKeyString(String key) {
        return mapManager.get(key).intValue();
    }

    public static Integer getNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).intValue();
    }

    public static Integer incrementNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).incrementAndGet();
    }

    public static Integer decrementNumbRequest(Constants.TYPE key) {
        return mapManager.get(key.getName()).decrementAndGet();
    }

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

    //    *********************************************************************************************
    public static void updateCheckConnect(String message) {
        messageCheckConnect = message;
    }

    //    *********************************************************************************************
    public static void updateLastTimeServiceReceiveRequest() {
        lastTimeServiceReceiveRequest = new Date();
    }

    //    *********************************************************************************************
    public static void updateMessageExceptionHistory(Exception e) {
        if (messageExceptionHistory.length() > 200) {
            messageExceptionHistory = "";
        }
        messageExceptionHistory += " | " + e.getMessage();
    }

    //    *********************************************************************************************
    public static void updateServiceMemoryStats() {
        serviceMemoryStats = "Free Memory / Total Memory / Max Memory (Mbs) : " + String.valueOf(Runtime.getRuntime().freeMemory() / 1048576)
                + " / " + String.valueOf(Runtime.getRuntime().totalMemory() / 1048576)
                + " / " + String.valueOf(Runtime.getRuntime().maxMemory() / 1048576)
        ;
    }


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
