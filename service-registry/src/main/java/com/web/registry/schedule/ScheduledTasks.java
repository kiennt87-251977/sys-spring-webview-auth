package com.web.registry.schedule;

import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

import com.web.registry.cache.ServiceCache;
import com.web.registry.config.ConfigScheduleService;
import com.web.registry.utils.DateUtilsV8;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {

    @Value("${spring.application.name}")
    private String serviceName;

    private final ServerProperties serverProperties;

    private Map<String, Date> mapInstancesLastTimeConnect = new HashMap<>();


    @Scheduled(fixedRateString = ConfigScheduleService.rateTimeApiService.updateCMSRate)
    public void showLogSystem() {
        try {

            if (ServiceCache.servicePort == null || ServiceCache.servicePort.trim().equals("")) {
                ServiceCache.serviceName = serviceName;
                ServiceCache.servicePort = String.valueOf(serverProperties.getPort());
                ServiceCache.serviceApplicationName = ServiceCache.serviceName + ":" + ServiceCache.servicePort;
            }
            ServiceCache.updateServiceMemoryStats();
            log.info("***************************************************");
            String message = "Service: " + ServiceCache.serviceApplicationName
                    + " (" + ServiceCache.numbService + ")"
                    + ", Start time : " + ServiceCache.getLogStartTime();
            log.info(message);

            log.info(ServiceCache.serviceMemoryStats);


            log.info("----------------------------------------------------------------------------------------");
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
            Applications applications = registry.getApplications();

            log.info("*** Numb Instances registereds: {}", applications.getRegisteredApplications().size());
            applications.getRegisteredApplications().forEach((registeredApplication) -> {
                registeredApplication.getInstances().forEach((instance) -> {
                    log.info("---------------------------");
                    log.info("==> Instance: {} ({}-{})"
                            , instance.getAppName(), instance.getInstanceId(), instance.getIPAddr()
                    );
                    log.info("==> Join Time: {}"
                            , DateUtilsV8.convertDateToString(new Date(instance.getLastUpdatedTimestamp()), DateUtilsV8.regex01));
                    mapInstancesLastTimeConnect.put(instance.getAppName() + " (" + instance.getInstanceId() + "-" + instance.getIPAddr() + ")", new Date());
                });
            });
            log.info("----------------------------------------------------------------------------------------");
            log.info("*** History Instances registereds");
            mapInstancesLastTimeConnect.forEach((s, date) -> {
                log.info("---------------------------");
                log.info("--> Check Instance: {}", s);
                log.info("--> Last Updated Time: {}", DateUtilsV8.convertDateToString(date, DateUtilsV8.regex01));

            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(cron = ConfigScheduleService.rateTimeApiService.reloadConfigRate)
    public void clearSysInfo() {
        ServiceCache.initValueMapManager();
        mapInstancesLastTimeConnect = new HashMap<>();
    }

    //************************************************************************************************************************


    //************************************************************************************************************************

}