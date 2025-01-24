package com.auth.core.aop;

import brave.Tracer;
import com.auth.core.aop.object.GlobalTraceCountObject;
import com.auth.core.aop.object.GlobalTraceIdUtils;
import com.auth.core.aop.object.GlobalTraceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class GlobalTraceFeignAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    Tracer tracer;

    private static Map<String, GlobalTraceCountObject> mapRateNumb = new HashMap<>();

    public static Map<String, GlobalTraceCountObject> getMapRateNumb() {
        return mapRateNumb;
    }

    //********************************************************************************************************
    @Around("execution(* com.web.ui.feign.*.*(..))")
    public Object traceService(ProceedingJoinPoint joinPoint) throws Throwable {
        String typeTrace = "Feign";
        long start = System.currentTimeMillis();
        String requestTraceId = GlobalTraceIdUtils.getRequestTraceId(request, GlobalTraceUtils.getRequestTraceIdInService(tracer));

        String requestTraceIdInService = GlobalTraceUtils.getRequestTraceIdInService(tracer);
        Integer leveltraceEvent = GlobalTraceUtils.traceEventLevel(requestTraceIdInService, mapRateNumb);
        Integer countNumbtraceEvent = GlobalTraceUtils.traceEventCount(requestTraceIdInService, mapRateNumb);

        String signature = joinPoint.getSignature().toShortString();
        String suffixeMessage = "(" + requestTraceId + ") " + typeTrace
                + ": (Level:" + leveltraceEvent + ")"
                + " (count: " + countNumbtraceEvent + ")";

        String messageRequest = GlobalTraceUtils.prefixRequest + suffixeMessage
                + ", [" + signature + "]";

        ObjectWriter ow = new ObjectMapper().writer();
        log.info(GlobalTraceUtils.createMessageParamInput(joinPoint, ow, messageRequest));

        Object result = joinPoint.proceed();

        long latency = System.currentTimeMillis() - start;

        String messageResponse = GlobalTraceUtils.prefixResponse + suffixeMessage
                + ", latency(ms): " + latency
                + ", (" + signature + ")";

        messageResponse += ", Result: ";
        log.info(GlobalTraceUtils.createMessageRsponseOutput(result, ow, messageResponse));

        GlobalTraceUtils.traceEventLevelTerninate(tracer, mapRateNumb);

        return result;
    }


}
