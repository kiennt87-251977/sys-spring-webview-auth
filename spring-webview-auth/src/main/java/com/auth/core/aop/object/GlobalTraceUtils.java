package com.auth.core.aop.object;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.auth.core.utils.DateUtilsV8;
import com.auth.core.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalTraceUtils {

    public static String prefixRequest = "---> Req";
    public static String prefixResponse = "<--- Response";
    private static String messageConvertError = "Cannot parse response to Json: ";

    public static String createMessageParamInput(ProceedingJoinPoint joinPoint, ObjectWriter ow, String preMessageRequest) {
        String message = preMessageRequest;
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            try {
                message += "Parameters: " + StringUtils.removePasswordChar(ow.writeValueAsString(joinPoint.getArgs()));
            } catch (Exception ex) {
                message += messageConvertError + StringUtils.removePasswordChar(Arrays.toString(joinPoint.getArgs()));
            }
        }
        return message;
    }

    public static String createMessageRsponseOutput(Object result, ObjectWriter ow, String preMessageRequest) {
        String message = preMessageRequest;
        if (result != null) {
            try {
                message += StringUtils.limitResponseStringLength(ow.writeValueAsString(result));
            } catch (Exception ex) {
                message += GlobalTraceUtils.messageConvertError + result;
            }
        }
        return message;
    }

    public static Integer getCountNumbByTraceId(Map<String, GlobalTraceCountObject> mapRateNumb, String traceId) {
        if (traceId != null && mapRateNumb.get(traceId) != null) {
            return mapRateNumb.get(traceId).getNumbRate().intValue();
        } else {
            return null;
        }
    }

    public static void clearMapWithExpireTime(Map<String, GlobalTraceCountObject> mapRateNumb, Integer minutes) {
        Set<String> list = mapRateNumb.keySet();
        List<String> listtemp = new ArrayList<>();
        for (String elem : list) {
            GlobalTraceCountObject object = mapRateNumb.get(elem);
            Date createdDate = object.getDateCreated();
            if (createdDate.before(DateUtilsV8.convertDatePlusByField(new Date(), Calendar.MINUTE, -minutes))) {
                listtemp.add(elem);
            }
        }

        for (String elem : listtemp) {
            GlobalTraceCountObject object = mapRateNumb.get(elem);
            Date createdDate = object.getDateCreated();
            if (createdDate.before(DateUtilsV8.convertDatePlusByField(new Date(), Calendar.MINUTE, -minutes))) {
                mapRateNumb.remove(elem);
            }
        }
    }


    public static String getRequestTraceIdInService(Tracer tracer) {
        try {
            return tracer.currentSpan().context().traceIdString();
        } catch (Exception e) {
            return null;
        }
    }


    public static Integer traceEventCount(String requestTraceIdInService, Map<String, GlobalTraceCountObject> mapRateNumb) {
        try {
            if (requestTraceIdInService != null) {
                return traceEventCountExecute(mapRateNumb, requestTraceIdInService);
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static int traceEventCountExecute(Map<String, GlobalTraceCountObject> mapRateNumb, String traceId) {
        if (mapRateNumb.get(traceId) == null) {
            mapRateNumb.putIfAbsent(traceId, new GlobalTraceCountObject(traceId, new AtomicInteger(0), new AtomicInteger(0), new Date(), false));
        }
        // count traceEvent numb
        return mapRateNumb.get(traceId).getNumbRate().incrementAndGet();
    }


    public static Integer traceEventLevel(String requestTraceIdInService, Map<String, GlobalTraceCountObject> mapRateNumb) {
        try {
            if (requestTraceIdInService != null) {
                return traceEventLevelExecute(mapRateNumb, requestTraceIdInService);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Integer traceEventLevelTerninate(Tracer tracer, Map<String, GlobalTraceCountObject> mapRateNumb) {
        try {
            String requestTraceIdInService = getRequestTraceIdInService(tracer);
            if (requestTraceIdInService != null) {
                return traceEventLevelExecuteTerninate(mapRateNumb, requestTraceIdInService);
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static Integer traceEventLevelExecute(Map<String, GlobalTraceCountObject> mapRateNumb, String traceId) {
        if (mapRateNumb.get(traceId) == null) {
            mapRateNumb.putIfAbsent(traceId, new GlobalTraceCountObject(traceId, new AtomicInteger(0), new AtomicInteger(0), new Date(), false));
        }
        // count traceEvent level
        return mapRateNumb.get(traceId).getLevel().incrementAndGet();
    }

    private static Integer traceEventLevelExecuteTerninate(Map<String, GlobalTraceCountObject> mapRateNumb, String traceId) {
        if (mapRateNumb.get(traceId) == null) {
            mapRateNumb.putIfAbsent(traceId, new GlobalTraceCountObject(traceId, new AtomicInteger(0), new AtomicInteger(0), new Date(), false));
        }
        // remove traceEvent level
        return mapRateNumb.get(traceId).getLevel().decrementAndGet();
    }


}
