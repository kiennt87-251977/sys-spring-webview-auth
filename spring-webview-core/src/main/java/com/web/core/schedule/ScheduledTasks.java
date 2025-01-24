package com.web.core.schedule;

import com.web.core.aop.GlobalTraceRepositoryAspect;
import com.web.core.aop.GlobalTraceServiceAspect;
import com.web.core.aop.object.GlobalTraceUtils;
import com.web.core.configuration.ConfigSchedule;
import com.web.core.configuration.cache.ServiceCache;
import com.web.core.domain.constant.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {


    @Value("${spring.application.name}")
    private String serviceName;


    @Value("${service.test.mode}")
    protected Boolean testMode;


    //************************************************************************************************************************


    //************************************************************************************************************************

    private final ServerProperties serverProperties;


    @Scheduled(fixedRateString = ConfigSchedule.rateTimeApiService.updateCMSRate)
    public void updateServiceInfo() {
        ServiceCache.updateServiceMemoryStats();
    }

    @Scheduled(fixedRateString = ConfigSchedule.rateTimeApiService.updateCMSRate)
    public void showLogSystem() {

        if (ServiceCache.servicePort == null || ServiceCache.servicePort.trim().equals("")) {
            ServiceCache.servicePort = String.valueOf(serverProperties.getPort());
            ServiceCache.serviceName = serviceName;
            ServiceCache.serviceApplicationName = ServiceCache.serviceName + ":" + ServiceCache.servicePort;
            keyInfor01 = "Name Service: " + ServiceCache.serviceApplicationName + "(" + ServiceCache.numbService + ")";
        }

        try {
            log.info("***************************************************");
            log.info("Service: {} ({}), Start time : {}"
                    , ServiceCache.serviceApplicationName, ServiceCache.numbService, ServiceCache.getLogStartTime());

            log.info("Numb Request (Entry/Current) : {} / {} | Request (Success/Fail) : {} / {}"
                    , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_ENTRY)
                    , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_CURRENT)
                    , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_SUCCESS)
                    , ServiceCache.getNumbRequest(Constants.TYPE.NUMB_REQUEST_FAIL)
            );

            for (String elem : ServiceCache.listManagerResponse) {
                log.info("-> Numb Response [{}] : {}", elem, ServiceCache.getNumbRequestByKeyString(elem));
            }


            log.info("Last Time Receive Request : " + ServiceCache.lastTimeServiceReceiveRequest);

            log.info(ServiceCache.serviceMemoryStats);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("Service: {} : {}, Mode run: {}", ServiceCache.serviceName, serverProperties.getPort(), !testMode);

    }

    @Scheduled(cron = ConfigSchedule.rateTimeApiService.reloadConfigRate)
    public void clearSysInfo() {
        ServiceCache.initValueMapManager();
    }

    //************************************************************************************************************************

    private static String keyInfor01 = "Name Service: ";

    //************************************************************************************************************************

    //************************************************************************************************************************

    /**
     * Clear trace data.
     */
    @Scheduled(fixedRate = 60000)
    public void clearTraceData() {
        GlobalTraceUtils.clearMapWithExpireTime(GlobalTraceRepositoryAspect.getMapRateNumb(), 3);
        GlobalTraceUtils.clearMapWithExpireTime(GlobalTraceServiceAspect.getMapRateNumb(), 3);
    }

    //************************************************************************************************************************


}