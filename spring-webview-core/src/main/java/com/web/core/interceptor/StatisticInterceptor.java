package com.web.core.interceptor;

import com.web.core.configuration.cache.ServiceCache;
import com.web.core.domain.constant.Constants;
import com.web.core.filter.GlobalTraceIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class StatisticInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_ENTRY);
        ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT);

        logRequestEntryInfo(GlobalTraceIdUtils.getRequestTraceId(request, null), "Input");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {
        // You can add attributes in the modelAndView
        // and use that in the view page
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.error(ex.getMessage(), ex);
            ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_FAIL);
            ServiceCache.updateMessageExceptionHistory(ex);
        } else {
            ServiceCache.incrementNumbRequest(Constants.TYPE.NUMB_REQUEST_SUCCESS);
        }
        ServiceCache.decrementNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT);

//        ServiceCache.incrementNumbByResponse(String.valueOf(response.getStatus()));

        logRequestEntryInfo(GlobalTraceIdUtils.getRequestTraceId(request, null), "Output");
    }


    private static void logRequestEntryInfo(String requestTraceId, String logType) {
        String message = "Request[{}] {}, Numb (Entry/Current): {} / {}";
        log.info(message, requestTraceId
                , logType
                , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_ENTRY)
                , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT));
    }
}
