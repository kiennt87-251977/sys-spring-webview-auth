package com.auth.core.schedule;


import com.auth.core.schedule.cache.ServiceCache;
import com.auth.core.schedule.config.ConfigScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The type Scheduled tasks sys.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasksSys {

    @Value("${spring.application.name}")
    private String serviceName;

    //************************************************************************************************************************


    //************************************************************************************************************************

    private final ServerProperties serverProperties;

    /**
     * Update service info.
     */
    @Scheduled(fixedRateString = ConfigScheduleService.rateTimeApiService.updateCMSRate)
    public void updateServiceInfo() {
        ServiceCache.updateServiceMemoryStats();
    }

    /**
     * Show log system.
     */

    @Scheduled(fixedRateString = ConfigScheduleService.rateTimeApiService.updateCMSRate)
    public void showLogSystem() {

        if (ServiceCache.servicePort == null || ServiceCache.servicePort.trim().equals("")) {
            ServiceCache.servicePort = String.valueOf(serverProperties.getPort());
            ServiceCache.serviceName = serviceName;
            ServiceCache.serviceApplicationName = ServiceCache.serviceName + ":" + ServiceCache.servicePort;

        }

        try {
            log.info("***************************************************");
            log.info("Service: {} ({}), Start time : {}"
                    , ServiceCache.serviceApplicationName, ServiceCache.numbService, ServiceCache.getLogStartTime());

            log.info(ServiceCache.serviceMemoryStats);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    //************************************************************************************************************************

    //************************************************************************************************************************

}