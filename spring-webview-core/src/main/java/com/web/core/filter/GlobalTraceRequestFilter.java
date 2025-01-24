package com.web.core.filter;

import brave.Tracer;
import com.web.core.configuration.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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

    private final Tracer tracer;

    @Autowired
    public GlobalTraceRequestFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        long starTime = System.currentTimeMillis();

        String requestURI = httpServletRequest.getRequestURI();
        String requestMethod = httpServletRequest.getMethod();


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


        String requestTraceId = httpServletRequest.getHeader(GlobalTraceIdUtils.keyHeaderNameTraceId);
        if (requestTraceId == null) {
            requestTraceId = GlobalTraceIdUtils.getTraceId(tracer);
            httpServletRequest.setAttribute(GlobalTraceIdUtils.keyHeaderNameTraceId, requestTraceId);
        }


        StringBuilder dataParametersInput = new StringBuilder();
        Map<String, String[]> mapParametersInput = httpServletRequest.getParameterMap();
        for (String elem : mapParametersInput.keySet()) {
            try {
                dataParametersInput.append(elem).append("=").append(Arrays.toString(mapParametersInput.get(elem))).append(", ");
            } catch (Exception ignored) {

            }
        }


        log.info("Request[{}]: URI: [{}]"
                        + ", Method: {}"
                        + ", Header {}"
                        + ", Parameters {{}}"
                , requestTraceId
                , requestURI
                , requestMethod
                , data.toString()
                , dataParametersInput.toString()
        );


        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(httpServletResponse);
        filterChain.doFilter(req, resp);

        StringBuilder dataParametersOutput = new StringBuilder();
        Map<String, String[]> mapParametersOut = req.getParameterMap();
        for (String elem : mapParametersOut.keySet()) {
            try {
                dataParametersOutput.append(elem).append("=").append(Arrays.toString(mapParametersOut.get(elem))).append(", ");
            } catch (Exception ignored) {

            }
        }

        byte[] requestBody = req.getContentAsByteArray();
        String stringRequestBody = StringUtils.limitResponseStringLength(new String(requestBody, StandardCharsets.UTF_8));

        byte[] responseBody = resp.getContentAsByteArray();
        String stringResponseBody = StringUtils.limitResponseStringLength(new String(responseBody, StandardCharsets.UTF_8));


        log.info("Response[{}]: URI: [{}]"
                        + ", Method: {}"
                        + ", Http StatusEnum: {}"
                        + ", Latency(ms): {}"
                        + ", Parameters: {{}}"
                        + ", Request body: {}"
                        + ", Response body: {}"
                , requestTraceId
                , requestURI
                , requestMethod
                , httpServletResponse.getStatus()
                , (System.currentTimeMillis() - starTime)
                , dataParametersOutput.toString()
                , stringRequestBody
                , stringResponseBody
        );


        resp.copyBodyToResponse();
    }


}
