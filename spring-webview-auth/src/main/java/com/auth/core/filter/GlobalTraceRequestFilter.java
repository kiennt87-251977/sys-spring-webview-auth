package com.auth.core.filter;

import brave.Tracer;
import com.auth.core.schedule.cache.ServiceCache;
import com.auth.core.schedule.constant.Constants;
import com.auth.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalTraceRequestFilter extends OncePerRequestFilter {
    public static String keyHeaderNameTraceId = "x-gate-way-trace-id";
    public static String keyHeaderAuthBridge = "auth_bridge";


    @Autowired
    Tracer tracer;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_ENTRY);
        ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT);
        ServiceCache.updateLastTimeServiceReceiveRequest();


        long starTime = System.currentTimeMillis();

        String requestURI = httpServletRequest.getRequestURI();
        String requestMethod = httpServletRequest.getMethod();
        String requestTraceId = httpServletRequest.getHeader(keyHeaderNameTraceId);
        if (requestTraceId == null) {
            requestTraceId = ServiceCache.servicePort + "-" + ServiceCache.numbService + "-" + GlobalTraceIdUtils.getTraceId(tracer);
            httpServletRequest.setAttribute(keyHeaderNameTraceId, requestTraceId);
        }

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        StringBuilder data = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            if (data.length() != 0) {
                data.append("|");
            }
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            data.append(String.format("%s: %s", key, value));
        }

        StringBuilder dataParametersInput = new StringBuilder();
        Map<String, String[]> mapParametersInput = httpServletRequest.getParameterMap();
        for (String elem : mapParametersInput.keySet()) {
            try {
                dataParametersInput.append(elem).append("=").append(Arrays.toString(mapParametersInput.get(elem))).append(", ");
            } catch (Exception ignored) {

            }
        }

        dataParametersInput = StringUtils.removePasswordChar(String.valueOf(dataParametersInput));

        log.info("===> Request({}): URI: ({})"
                        + ", Method: {}"
                        + ", Header {}"
                        + ", Parameters {{}}"
                , requestTraceId
                , requestURI
                , requestMethod
                , data.toString()
                , dataParametersInput
        );

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(httpServletResponse);

        AddParamsToHeader addParamsToHeader = new AddParamsToHeader(req);
        addParamsToHeader.putHeader(keyHeaderNameTraceId, requestTraceId);

        filterChain.doFilter(addParamsToHeader, resp);

        StringBuilder dataParametersOutput = new StringBuilder();
        Map<String, String[]> mapParametersOut = req.getParameterMap();
        for (String elem : mapParametersOut.keySet()) {
            try {
                dataParametersOutput.append(elem).append("=").append(Arrays.toString(mapParametersOut.get(elem))).append(", ");
            } catch (Exception ignored) {

            }
        }
        dataParametersOutput = StringUtils.removePasswordChar(String.valueOf(dataParametersOutput));


        byte[] requestBody = req.getContentAsByteArray();
        String stringRequestBody = StringUtils.limitResponseStringLength(new String(requestBody, StandardCharsets.UTF_8));

        byte[] responseBody = resp.getContentAsByteArray();
        String stringResponseBody = StringUtils.limitResponseStringLength(new String(responseBody, StandardCharsets.UTF_8));

        String traceId = null;
        try {
            JSONObject jsonObject = new JSONObject(new String(responseBody, StandardCharsets.UTF_8));
            traceId = String.valueOf(jsonObject.get("traceId"));
            resp.setHeader(keyHeaderNameTraceId, requestTraceId);
        } catch (JSONException ignored) {
        }


        try {
            ServiceCache.decrementNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT);
            if (httpServletResponse.getStatus() == (HttpStatus.OK.value())) {
                ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_SUCCESS);
            } else {
                ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_FAIL);
            }
        } catch (Exception ignored) {
        }


        log.info("<==== Response({}): Trace-id ({}), URI: ({})"
                        + ", Method: {}"
                        + ", Http Status: {}"
                        + ", Latency(ms): {}"
                        + ", Parameters: {{}}"
                        + ", Request body: {}"
                        + ", Response body: {}"
                , requestTraceId
                , traceId
                , requestURI
                , requestMethod
                , httpServletResponse.getStatus()
                , (System.currentTimeMillis() - starTime)
                , dataParametersOutput
                , stringRequestBody
                , stringResponseBody
        );

        resp.copyBodyToResponse();


    }
}
