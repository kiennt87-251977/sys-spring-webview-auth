package com.auth.core.filter;

import brave.Tracer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GlobalTraceIdUtils {
    public static String keyHeaderNameTraceId = GlobalTraceRequestFilter.keyHeaderNameTraceId;


    private static String getCurrentTraceId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(keyHeaderNameTraceId);
        }
        return StringUtils.EMPTY;
    }

    public static HttpHeaders getCurrentHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        httpHeaders.setAccept(acceptableMediaTypes);

        httpHeaders.setCacheControl(CacheControl.noCache());

        httpHeaders.add(keyHeaderNameTraceId, getCurrentTraceId());
        return httpHeaders;
    }


    static String getTraceId(Tracer tracer) {
        String requestTraceId = null;
        try {
            requestTraceId = tracer.currentSpan().context().traceIdString();
        } catch (Exception e) {
            requestTraceId = GlobalTraceIdUtils.generatingRandomAlphabetic();
        }
        return requestTraceId;
    }

    private static String generatingRandomAlphabetic() {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getRequestTraceId(HttpServletRequest request, String requestTraceIdInService) {
        try {
            String requestTraceId = request.getHeader(keyHeaderNameTraceId);
            if (requestTraceId == null) {
                requestTraceId = String.valueOf(request.getAttribute(keyHeaderNameTraceId));
            }
            return requestTraceId;
        } catch (Exception e) {
            return Objects.requireNonNullElseGet(requestTraceIdInService, () -> "null_traceId_" + generatingRandomAlphabetic());
        }
    }
}
