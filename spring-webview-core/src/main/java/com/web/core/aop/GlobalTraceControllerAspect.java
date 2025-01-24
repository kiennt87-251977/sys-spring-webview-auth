package com.web.core.aop;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.web.core.aop.object.GlobalTraceUtils;
import com.web.core.filter.GlobalTraceIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class GlobalTraceControllerAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    Tracer tracer;

    @Around("execution(* com.web.ui.controller.*.*(..))")
    public Object traceController(ProceedingJoinPoint joinPoint) throws Throwable {
        String typeTrace = "Controller";
        String requestTraceId = GlobalTraceIdUtils.getRequestTraceId(request, GlobalTraceUtils.getRequestTraceIdInService(tracer));

        String signature = joinPoint.getSignature().toShortString();
        String suffixeMessage = "(" + requestTraceId + ") " + typeTrace
                + ": (" + signature + ")";
        String messageRequest = GlobalTraceUtils.prefixRequest + suffixeMessage;

        ObjectWriter ow = new ObjectMapper().writer();
        log.info(GlobalTraceUtils.createMessageParamInput(joinPoint, ow, messageRequest));


        Object result = joinPoint.proceed();

        String messageResponse = GlobalTraceUtils.prefixResponse + suffixeMessage;
        messageResponse += ", Result: ";

        log.info(GlobalTraceUtils.createMessageRsponseOutput(result, ow, messageResponse));


        return result;
    }
}
